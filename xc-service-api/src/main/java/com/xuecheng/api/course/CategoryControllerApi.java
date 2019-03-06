package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 蔡闯王
 * @date 2019/2/5 20:41
 */
@Api(value = "分类管理接口", description = "提供课程的增删改查")
public interface CategoryControllerApi {
    /**
     * 查询所有分类
     *
     * @return
     */
    @ApiOperation("查询所有分类")
    public CategoryNode findCategoryList();
}
