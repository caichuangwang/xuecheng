package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachPlanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 蔡闯王
 * @date 2019/1/31 22:03
 */
@Mapper
public interface TeachPlanMapper {
    /**
     * 根据课程id查询所有课程
     *
     * @param courseId
     * @return
     */
    public TeachPlanNode findTeachPlanList(String courseId);
}
