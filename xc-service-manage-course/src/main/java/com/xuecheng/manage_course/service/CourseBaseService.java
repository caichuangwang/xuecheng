package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 蔡闯王
 * @date 2019/2/6 11:00
 */
@Service
public class CourseBaseService {
    @Autowired
    private CourseBaseRepository courseBaseRepository;

    /**
     * 添加课程
     *
     * @param courseBase
     * @return
     */
    public ResponseResult saveCourseBase(CourseBase courseBase) {
        courseBase.setStatus("202001");
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id查询课程信息
     *
     * @param courseId
     * @return
     */
    public CourseBase findByCourseId(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 更新课程信息
     *
     * @param id
     * @param courseBase
     * @return
     */
    public ResponseResult updateCourseBase(String id, CourseBase courseBase) {
        CourseBase courseBaseOne = this.findByCourseId(id);
        if (courseBaseOne == null || courseBase == null){
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
