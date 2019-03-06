package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 蔡闯王
 * @date 2019/2/6 18:46
 */
@Service
public class CourseMarketService {
    @Autowired
    private CourseMarketRepository courseMarketRepository;

    /**
     * 根据课程id查询课程营销计划
     *
     * @param courseId
     * @return
     */
    public CourseMarket findById(String courseId) {
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 更新课程营销计划
     *
     * @param id
     * @param courseMarket
     * @return
     */
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        CourseMarket courseMarketOne = this.findById(id);
        if (courseMarketOne == null || courseMarket == null) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
