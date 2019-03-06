package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.TeachPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 蔡闯王
 * @date 2019/2/5 14:41
 */
public interface TeachPlanRepository extends JpaRepository<TeachPlan, String> {
    /**
     * 根据课程id和父节点查询课程计划
     *
     * @param courseId
     * @param parentId
     * @return
     */
    public List<TeachPlan> findByCourseidAndParentid(String courseId, String parentId);
}
