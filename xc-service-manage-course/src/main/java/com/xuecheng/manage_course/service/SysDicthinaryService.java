package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.dao.SysDicthinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 蔡闯王
 * @date 2019/2/5 21:09
 */
@Service
public class SysDicthinaryService {
    @Autowired
    private SysDicthinaryRepository sysDicthinaryRepository;

    /**
     * 根据数据字典查询
     *
     * @param type
     * @return
     */
    public SysDictionary findGrade(String type) {
        return sysDicthinaryRepository.findByDType(type);
    }
}
