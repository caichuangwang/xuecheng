package com.xuecheng.api.search;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 蔡闯王
 * @date 2019/2/13 18:04
 */
@Api(value = "课程搜索", description = "课程搜索", tags = {"课程搜索"})
public interface EsCourseControllerApi {
    /**
     * 课程搜索
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    @ApiOperation("课程搜索")
    public QueryResponseResult list(Integer page, Integer size, CourseSearchParam courseSearchParam);
}
