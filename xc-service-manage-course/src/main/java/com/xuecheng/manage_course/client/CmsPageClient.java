package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 蔡闯王
 * @date 2019/2/9 16:59
 */
@FeignClient(value = "XC-SERVICE-MANAGE-CMS") //指定远程调用的服务名
public interface CmsPageClient {
    /**
     * 根据id查询页面信息
     *
     * @param id
     * @return
     */
    @GetMapping("/cms/page/findOne/{id}")
    public CmsPageResult findOne(@PathVariable("id") String id);

    /**
     * 保存页面
     *
     * @param cmsPage
     * @return
     */
    @PostMapping("/cms/page/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    @PostMapping("/cms/page/postPageQuick")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
