package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 蔡闯王
 * @date 2019/1/23 20:24
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
