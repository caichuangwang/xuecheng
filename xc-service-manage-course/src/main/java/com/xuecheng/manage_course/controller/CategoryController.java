package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蔡闯王
 * @date 2019/2/5 20:42
 */
@RestController
public class CategoryController implements CategoryControllerApi {
    @Autowired
    private CategoryService categoryService;

    @Override
    @GetMapping("/category/list")
    public CategoryNode findCategoryList() {
        return categoryService.findCategoryList();
    }
}
