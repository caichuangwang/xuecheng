package com.xuecheng.manage_course.testdao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.manage_course.ManageCourseApplication;
import com.xuecheng.manage_course.client.CmsPageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 蔡闯王
 * @date 2019/2/9 17:10
 */
@SpringBootTest(classes = ManageCourseApplication.class)
@RunWith(SpringRunner.class)
public class TestFeign {
    @Autowired
    CmsPageClient cmsPageClient; //接口代理对象，由Feign生成代理对象

    @Test
    public void testRibbon(){
        //发起远程调用
        CmsPageResult cmsPageResult = cmsPageClient.findOne("5a754adf6abb500ad05688d9");
        System.out.println(cmsPageResult.getCmsPage());

    }
}
