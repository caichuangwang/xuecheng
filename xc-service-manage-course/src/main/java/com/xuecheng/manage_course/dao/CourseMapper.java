package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachPlanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 蔡闯王
 * @date 2019/1/31 22:03
 */
@Mapper
public interface CourseMapper {
    /**
     * 根据课程id查询所有课程
     *
     * @param courseId
     * @return
     */
    public TeachPlanNode findTeachPlanList(String courseId);

    /**
     * 查询所有课程信息，带分页
     *
     * @param courseListRequest
     * @return
     */
    public List<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);
}
