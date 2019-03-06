package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 蔡闯王
 * @date 2019/2/10 11:29
 */
@Data
@ToString
@NoArgsConstructor
public class CourseView implements Serializable {
    /**
     * 基础信息
     */
    private CourseBase courseBase;
    /**
     * 课程营销
     */
    private CourseMarket courseMarket;
    /**
     * 课程图片
     */
    private CoursePic coursePic;
    /**
     * 教学计划
     */
    private TeachPlanNode teachPlanNode;
}
