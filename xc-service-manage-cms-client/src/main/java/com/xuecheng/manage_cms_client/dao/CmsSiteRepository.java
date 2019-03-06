package com.xuecheng.manage_cms_client.dao;


import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 蔡闯王
 * @date 2019/1/31 19:57
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
