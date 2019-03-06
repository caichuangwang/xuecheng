package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 蔡闯王
 * @date 2019/2/5 16:39
 */
public interface CourseBaseRepository extends JpaRepository<CourseBase, String> {
}
