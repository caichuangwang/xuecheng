package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息监听类
 *
 * @author 蔡闯王
 * @date 2019/1/31 21:00
 */
@Component
public class ConsumerSavePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerSavePage.class);

    @Autowired
    private PageService pageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void savePage(String msg) {
        Map<String, String> map = JSON.parseObject(msg, Map.class);
        String pageId = map.get("pageId");
        CmsPage cmsPage = pageService.findCmsPageById(pageId);
        if (cmsPage == null) {
            LOGGER.error("cmsPage is null,pageId:{}", pageId);
            return;
        }
        pageService.savePageToServer(pageId);
    }
}
