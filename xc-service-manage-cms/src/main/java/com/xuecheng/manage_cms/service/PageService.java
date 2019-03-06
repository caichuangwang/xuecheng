package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author 蔡闯王
 * @date 2019/1/23 22:49
 */
@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (page <= 0) {
            page = 1;
        }
        //为了适应mongodb的接口将页码减1
        page -= 1;
        if (size <= 0) {
            size = 20;
        }
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //设置查询条件
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //创建条件匹配器
        //页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建条件对象
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //创建分页对象
        Pageable pageable = new PageRequest(page, size);
        //分页查询
        Page<CmsPage> list = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        //设置结果集
        queryResult.setList(list.getContent());
        //设置总页数
        queryResult.setTotal(list.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 添加页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult save(CmsPage cmsPage) {
        if (cmsPage == null) {
            //抛出非法参数异常
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        //检验要保存的页面是否存在
        CmsPage cmsPageOne = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPageOne != null) {
            //抛出页面已存在异常
            //throw new CustomException(CommonCode.CMS_ADDPAGE_EXISTS);
            //更新
            this.update(cmsPageOne.getPageId(), cmsPageOne);
        } else {
            //保存
            cmsPageRepository.save(cmsPage);
        }

        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    /**
     * 根据id查询页面
     *
     * @param id
     * @return
     */
    public CmsPageResult findOne(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            return new CmsPageResult(CommonCode.SUCCESS, optional.get());
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 修改页面
     *
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage cmsPageOne = this.findOne(id).getCmsPage();
        //判断数据库是否存在
        if (cmsPageOne != null) {
            cmsPageOne.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            cmsPageOne.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            cmsPageOne.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            cmsPageOne.setPageName(cmsPage.getPageName());
            //更新访问路径
            cmsPageOne.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            cmsPageOne.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更近数据路径
            cmsPageOne.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage saveCmsPage = cmsPageRepository.save(cmsPageOne);
            if (saveCmsPage != null) {
                return new CmsPageResult(CommonCode.SUCCESS, saveCmsPage);
            }
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 根据id删除页面
     *
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {
        CmsPage cmsPageOne = this.findOne(id).getCmsPage();
        if (cmsPageOne != null) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 查询所有站点
     *
     * @return
     */
    public QueryResponseResult querySite() {
        List<CmsSite> list = cmsSiteRepository.findAll();
        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setList(list);

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 查询所有模板
     *
     * @return
     */
    public QueryResponseResult queryTemplate() {
        List<CmsTemplate> list = cmsTemplateRepository.findAll();
        QueryResult<CmsTemplate> queryResult = new QueryResult<>();
        queryResult.setList(list);

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 执行生成静态化页面方法
     *
     * @param id
     * @return
     */
    public String generatePageHtml(String id) {
        //1.获取数据
        Map model = getModel(id);
        if (model == null) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //2.获取数据模板
        String stringTemplate = getStringTemplate(id);
        if (StringUtils.isEmpty(stringTemplate)) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //3.生成静态页面
        return generateHtml(stringTemplate, model);
    }

    /**
     * 生成静态化页面
     *
     * @param stringTemplate
     * @param model
     * @return
     */
    private String generateHtml(String stringTemplate, Map model) {
        //1.创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.配置模板
        //2.1创建字符串模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", stringTemplate);
        //2.2加载字符串模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            Template template = configuration.getTemplate("template");
            //静态化页面
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取静态化页面字符串模板
     *
     * @param id
     * @return
     */
    private String getStringTemplate(String id) {
        CmsPage cmsPage = this.findById(id);
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //从GridFS中取模板文件内容
            //根据id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开一个下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            try {
                //从流中获取数据
                return IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取静态化页面数据
     *
     * @param id
     * @return
     */
    private Map getModel(String id) {
        CmsPage cmsPage = this.findById(id);
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //发送请求，获取数据
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        return entity.getBody();
    }

    /**
     * 根据页面id查询页面
     *
     * @param id
     * @return
     */
    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    public CmsConfig findOneModel(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //1.添加页面
        CmsPageResult cmsPageResult = this.save(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }
        String pageId = cmsPageResult.getCmsPage().getPageId();
        //2.发布页面
        ResponseResult responseResult = this.publish(pageId);
        if (!responseResult.isSuccess()) {
            return new CmsPostPageResult(CommonCode.FAIL, null);
        }
        //3.获取页面url
        //页面url=站点域名+站点webpath+页面webpath+页面名称
        CmsPage resultCmsPage = cmsPageResult.getCmsPage();
        String siteId = resultCmsPage.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //3.1获取域名
        String siteDomain = cmsSite.getSiteDomain();
        //3.2获取站点webpath
        String siteWebPath = cmsSite.getSiteWebPath();
        //3.3获取页面webpath
        String pageWebPath = resultCmsPage.getPageWebPath();
        //3.4获取页面名称
        String pageName = resultCmsPage.getPageName();
        String pageUrl = siteDomain + siteWebPath + pageWebPath + pageName;

        return new CmsPostPageResult(CommonCode.SUCCESS, pageUrl);
    }

    /**
     * 发布页面
     *
     * @param pageId
     * @return
     */
    public ResponseResult publish(String pageId) {
        //1.发布页面
        String pageHtml = this.generatePageHtml(pageId);
        //2.将静态化页面文件存储到GridFs中
        this.saveHtml(pageId, pageHtml);
        //3.发送消息
        sendMessage(pageId);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 向rabbtimq发送消息
     *
     * @param pageId
     */
    private void sendMessage(String pageId) {
        CmsPage cmsPage = this.findById(pageId);
        Map<String, String> map = new HashMap<>();
        map.put("pageId", pageId);
        String jsonString = JSON.toJSONString(map);
        //发送消息
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, cmsPage.getSiteId(), jsonString);
    }

    private CmsPage saveHtml(String pageId, String pageHtml) {
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId = null;
        try {
            InputStream inputStream = IOUtils.toInputStream(pageHtml, "utf-8");
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    private CmsSite findCmsSiteById(String id) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
