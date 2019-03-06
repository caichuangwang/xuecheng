package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 蔡闯王
 * @date 2019/1/29 16:10
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig, String> {
}
