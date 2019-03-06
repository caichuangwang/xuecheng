package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 蔡闯王
 * @date 2019/2/5 20:44
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有分类
     *
     * @return
     */
    public CategoryNode findCategoryList() {
        return categoryMapper.findCategoryList();
    }
}
