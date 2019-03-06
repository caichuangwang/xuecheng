package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 蔡闯王
 * @date 2019/2/5 20:59
 */
@Api(value = "数据字典接口", description = "提供数据字典接口的管理、查询功能")
public interface SysDicthinaryControllerApi {
    /**
     * 数据字典
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "数据字典查询接口")
    public SysDictionary getByType(String type);
}
