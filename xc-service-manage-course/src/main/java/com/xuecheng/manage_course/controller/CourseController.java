package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.TeachPlan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachPlanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_course.service.CourseBaseService;
import com.xuecheng.manage_course.service.CourseMarketService;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 蔡闯王
 * @date 2019/2/5 13:39
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private CourseMarketService courseMarketService;

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachPlanNode findTeachPlanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachPlanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachPlan(@RequestBody TeachPlan teachPlan) {
        return courseService.addTeachPlan(teachPlan);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") Integer page, @PathVariable("size") Integer size, CourseListRequest courseListRequest) {
        Page<CourseInfo> listPage = (Page<CourseInfo>) courseService.findCourseListPage(page, size, courseListRequest);
        List<CourseInfo> courseInfoList = listPage.getResult();

        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(courseInfoList);
        queryResult.setTotal(listPage.getTotal());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult saveCourse(@RequestBody CourseBase courseBase) {
        return courseBaseService.saveCourseBase(courseBase);
    }

    @Override
    @GetMapping("/findOne/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseBaseService.findByCourseId(courseId);
    }

    @Override
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        return courseBaseService.updateCourseBase(id, courseBase);
    }

    @Override
    @GetMapping("/findCourseMarketById/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseMarketService.findById(courseId);
    }

    @Override
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        return courseMarketService.updateCourseMarket(id, courseMarket);
    }

    @Override
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.getCourseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    /**
     * 课程发布
     *
     * @param id
     * @return
     */
    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult release(@PathVariable("id") String id) {
        return courseService.release(id);
    }
}
