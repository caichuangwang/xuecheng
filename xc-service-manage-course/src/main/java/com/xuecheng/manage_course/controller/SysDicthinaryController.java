package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.SysDicthinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蔡闯王
 * @date 2019/2/5 21:02
 */
@RestController
@RequestMapping("/sys")
public class SysDicthinaryController implements SysDicthinaryControllerApi {
    @Autowired
    private SysDicthinaryService sysDicthinaryService;

    @Override
    @GetMapping("/dictionary/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type) {
        return sysDicthinaryService.findGrade(type);
    }
}
