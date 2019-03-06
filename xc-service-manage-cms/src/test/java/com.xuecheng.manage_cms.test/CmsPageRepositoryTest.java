package com.xuecheng.manage_cms.test;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.ManageCmsApplication;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author 蔡闯王
 * @date 2019/1/23 20:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void testFindOne(){
        Optional<CmsPage> cmsPage = cmsPageRepository.findById("5b4b1d8bf73c6623b03f8cec");
        System.out.println(cmsPage);
    }
}
