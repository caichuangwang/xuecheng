package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.TeachPlan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachPlanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponse;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 蔡闯王
 * @date 2019/2/5 13:05
 */
@Api(value = "课程管理接口", description = "提供课程的增删改查")
public interface CourseControllerApi {
    /**
     * 根据课程id查询所有课程计划
     *
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程计划")
    public TeachPlanNode findTeachPlanList(String courseId);

    /**
     * 添加课程计划
     *
     * @param teachPlan
     * @return
     */
    @ApiOperation("添加课程计划")
    public ResponseResult addTeachPlan(TeachPlan teachPlan);

    /**
     * 查询所有课程
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @ApiOperation("查询我的课程列表")
    public QueryResponseResult findCourseList(Integer page, Integer size, CourseListRequest courseListRequest);

    /**
     * 添加课程
     *
     * @param courseBase
     * @return
     */
    public ResponseResult saveCourse(CourseBase courseBase);

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程基础信息")
    public CourseBase getCourseBaseById(String courseId);

    /**
     * 更新课程信息
     *
     * @param id
     * @param courseBase
     * @return
     */
    @ApiOperation("更新课程基础信息")
    public ResponseResult updateCourseBase(String id, CourseBase courseBase);

    /**
     * 根据课程id查询课程营销计划
     *
     * @param courseId
     * @return
     */
    @ApiOperation("查询课程营销信息")
    public CourseMarket getCourseMarketById(String courseId);

    /**
     * 更新课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @ApiOperation("更新课程营销信息")
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

    /**
     * 查询课程视图
     *
     * @param id
     * @return
     */
    @ApiOperation("课程视图查询")
    public CourseView courseview(String id);

    /**
     * 课程预览
     *
     * @param id
     * @return
     */
    @ApiOperation("课程预览")
    public CoursePublishResult preview(String id);

    /**
     * 课程发布
     *
     * @param id
     * @return
     */
    @ApiOperation("课程发布")
    public CoursePublishResult release(String id);
}
