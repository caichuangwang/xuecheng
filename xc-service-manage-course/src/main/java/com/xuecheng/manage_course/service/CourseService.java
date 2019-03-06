package com.xuecheng.manage_course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.TeachPlan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachPlanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 蔡闯王
 * @date 2019/2/5 13:34
 */
@Service
public class CourseService {
    @Value("${course-publish.dataUrlPre}")
    private String publishDataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publishPagePhysicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publishPageWebpath;
    @Value("${course-publish.siteId}")
    private String publishSiteId;
    @Value("${course-publish.templateId}")
    private String publishTemplateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeachPlanRepository teachPlanRepository;

    @Autowired
    private TeachPlanMapper teachPlanMapper;

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CmsPageClient cmsPageClient;

    /**
     * 根据课程id查询所有课程
     *
     * @param courseId
     * @return
     */
    public TeachPlanNode findTeachPlanList(String courseId) {
        return courseMapper.findTeachPlanList(courseId);
    }

    /**
     * 添加课程计划
     *
     * @param teachPlan
     * @return
     */
    @Transactional
    public ResponseResult addTeachPlan(TeachPlan teachPlan) {
        if (teachPlan == null || StringUtils.isEmpty(teachPlan.getCourseid()) || StringUtils.isEmpty(teachPlan.getPname())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        //1.判断该课程是否有父节点
        String parentid = teachPlan.getParentid();
        if (StringUtils.isEmpty(parentid)) {
            //1.1没有父节点
            List<TeachPlan> teachPlanList = teachPlanRepository.findByCourseidAndParentid(teachPlan.getCourseid(), "0");
            teachPlan.setParentid(teachPlanList.get(0).getId());
            teachPlan.setGrade("1");
        } else {
            //1.2有父节点
            //设置分类级别
            Optional<TeachPlan> optional = teachPlanRepository.findById(parentid);
            TeachPlan teachPlanOne = optional.get();
            if (teachPlanOne.getGrade().equals("1")) {
                teachPlan.setGrade("2");
            } else {
                teachPlan.setGrade("3");
            }
        }
        teachPlan.setStatus("0");
        teachPlanRepository.save(teachPlan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询所有课程信息，带分页
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    public Page<CourseInfo> findCourseListPage(Integer page, Integer size, CourseListRequest courseListRequest) {
        PageHelper.startPage(page, size);
        return (Page<CourseInfo>) courseMapper.findCourseListPage(courseListRequest);
    }

    private String getTeachPlanRoot(String courseId) {
        //校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();
        //调用dao查询teachplan表得到该课程的根结点（一级结点）
        List<TeachPlan> teachPlanList = teachPlanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachPlanList == null || teachPlanList.size() == 0) {
            //新增一个根结点
            TeachPlan teachPlanRoot = new TeachPlan();
            teachPlanRoot.setCourseid(courseId);
            teachPlanRoot.setPname(courseBase.getName());
            teachPlanRoot.setParentid("0");
            //1级
            teachPlanRoot.setGrade("1");
            //未发布
            teachPlanRoot.setStatus("0");
            teachPlanRepository.save(teachPlanRoot);
            return teachPlanRoot.getId();
        }
        //返回根结点的id
        return teachPlanList.get(0).getId();
    }

    /**
     * 查询课程视图
     *
     * @param id
     * @return
     */
    public CourseView getCourseView(String id) {
        CourseView courseView = new CourseView();
        //查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if (courseBaseOptional.isPresent()) {
            courseView.setCourseBase(courseBaseOptional.get());
        }
        //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if (courseMarketOptional.isPresent()) {
            courseView.setCourseMarket(courseMarketOptional.get());
        }
        //查询课程图片信息
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(id);
        if (coursePicOptional.isPresent()) {
            courseView.setCoursePic(coursePicOptional.get());
        }
        //查询课程计划
        TeachPlanNode teachPlanNode = teachPlanMapper.findTeachPlanList(id);
        courseView.setTeachPlanNode(teachPlanNode);

        return courseView;
    }

    /**
     * 课程预览
     *
     * @param id
     * @return
     */
    public CoursePublishResult preview(String id) {
        //查询课程基本信息
        CourseBase courseBaseOne = this.findCourseBaseById(id);
        //1.保存页面
        CmsPage cmsPage = new CmsPage();
        //站点id
        cmsPage.setSiteId(publishSiteId);
        //数据模型url
        cmsPage.setDataUrl(publishDataUrlPre + id);
        //页面名称
        cmsPage.setPageName(id + ".html");
        //页面别名，就是课程名称
        cmsPage.setPageAliase(courseBaseOne.getName());
        //页面物理路径
        cmsPage.setPagePhysicalPath(publishPagePhysicalpath);
        //页面webpath
        cmsPage.setPageWebPath(publishPageWebpath);
        //页面模板id
        cmsPage.setTemplateId(publishTemplateId);
        //远程调用保存页面接口
        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);

        //2.拼装页面预览url
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        return new CoursePublishResult(CommonCode.SUCCESS, previewUrl + cmsPageResult.getCmsPage().getPageId());
    }

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    private CourseBase findCourseBaseById(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new CustomException(CommonCode.INVALID_PARAM);
    }

    /**
     * 课程发布
     *
     * @return
     */
    @Transactional
    public CoursePublishResult release(String courseId) {
        CourseBase courseBase = this.findCourseBaseById(courseId);
        //1.调用cms一键发布接口将课程详情页面发布到服务器
        CmsPostPageResult cmsPostPageResult = this.publishPage(courseId);
        if(!cmsPostPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        //2.保存课程的发布状态为“已发布”
        //CourseBase courseBase = this.saveCoursePubState(id);
        if(courseBase == null){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        //保存课程索引信息
        //...

        //缓存课程的信息
        //...

        //获取页面的url
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    private CmsPostPageResult publishPage(String courseId){
        CourseBase courseBase = this.findCourseBaseById(courseId);
        //1.准备页面信息
        CmsPage cmsPage = new CmsPage();
        //站点id
        cmsPage.setSiteId(publishSiteId);
        //数据模型url
        cmsPage.setDataUrl(publishDataUrlPre+courseId);
        //页面名称
        cmsPage.setPageName(courseId+".html");
        //页面别名，就是课程名称
        cmsPage.setPageAliase(courseBase.getName());
        //页面物理路径
        cmsPage.setPagePhysicalPath(publishPagePhysicalpath);
        //页面webpath
        cmsPage.setPageWebPath(publishPageWebpath);
        //页面模板id
        cmsPage.setTemplateId(publishTemplateId);

        //2.调用cms一键发布接口将课程详情页面发布到服务器
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);

        return cmsPostPageResult;
    }
}
