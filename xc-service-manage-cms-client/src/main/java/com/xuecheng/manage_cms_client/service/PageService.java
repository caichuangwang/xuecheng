package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @author 蔡闯王
 * @date 2019/1/31 20:22
 */
@Service
public class PageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 保存页面到服务器
     *
     * @param pageId
     */
    public void savePageToServer(String pageId) {
        //1.设置页面路径
        CmsPage cmsPage = this.findCmsPageById(pageId);
        //1.1获取站点信息
        String siteId = cmsPage.getSiteId();
        //1.2获取站点id的物理路径
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        //1.3设置页面的物理路径
        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();

        //3.将html文件保存到服务器
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String htmlFileId = cmsPage.getHtmlFileId();
            inputStream = this.getFileById(htmlFileId);
            if(inputStream == null){
                LOGGER.error("getFileById InputStream is null ,htmlFileId:{}",htmlFileId);
                return ;
            }
            outputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据文件id从GridFS中查询文件内容
     *
     * @param fileId
     * @return
     */
    private InputStream getFileById(String fileId){
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据页面id查询页面信息
     *
     * @param pageId
     * @return
     */
    public CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 根据站点id查询站点信息
     *
     * @param siteId
     * @return
     */
    private CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
