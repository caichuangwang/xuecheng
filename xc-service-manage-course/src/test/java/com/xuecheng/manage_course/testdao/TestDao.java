package com.xuecheng.manage_course.testdao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachPlanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.dao.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 蔡闯王
 * @date 2019/2/5 13:50
 */
@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void testFindTeachPlanList() {
        TeachPlanNode teachPlanNode = courseMapper.findTeachPlanList("4028e581617f945f01617f9dabc40000");
        System.out.println(teachPlanNode);
    }

    @Test
    public void testPageHelper() {
        PageHelper.startPage(1, 10);//查询第一页，每页显示10条记录
        CourseListRequest courseListRequest = new CourseListRequest();
        Page<CourseInfo> courseListPage = (Page<CourseInfo>) courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> result = courseListPage.getResult();
        System.out.println(result);
    }
}
