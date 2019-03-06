package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 蔡闯王
 * @date 2019/2/5 20:00
 */
@Mapper
public interface CategoryMapper {
    /**
     * 查询所有分类
     *
     * @return
     */
    public CategoryNode findCategoryList();
}
