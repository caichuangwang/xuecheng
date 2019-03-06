package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author 蔡闯王
 * @date 2019/1/29 16:02
 */
@Api(value = "cms配置管理接口", description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {
    /**
     * 根据id查询页面静态化模板
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询静态化模板")
    public CmsConfig getModel(String id);
}
