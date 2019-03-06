package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 蔡闯王
 * @date 2019/2/5 21:06
 */
public interface SysDicthinaryRepository extends MongoRepository<SysDictionary, String> {
    /**
     * 根据数据字典查询
     *
     * @param type
     * @return
     */
    public SysDictionary findByDType(String type);
}
