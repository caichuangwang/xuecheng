package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 蔡闯王
 * @date 2019/1/23 20:24
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    /**
     * 根据网页名称，站点，网站路径查询
     *
     * @param pageName
     * @param siteId
     * @param pageWebPath
     * @return
     */
    public CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
