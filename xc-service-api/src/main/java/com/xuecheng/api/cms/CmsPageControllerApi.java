package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 蔡闯王
 * @date 2019/1/23 20:19
 */
@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    /**
     * 查询所有静态页面
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 添加网页
     *
     * @param cmsPage
     * @return
     */
    @ApiOperation("添加网页")
    public CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据id查询页面
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询页面")
    public CmsPageResult findOne(String id);

    /**
     * 修改页面
     *
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult update(String id, CmsPage cmsPage);

    /**
     * 根据id 删除页面
     *
     * @param id
     * @return
     */
    @ApiOperation("通过ID删除页面")
    public ResponseResult delete(String id);

    /**
     * 查询所有站点
     *
     * @return
     */
    @ApiOperation("查询所有站点")
    public QueryResponseResult querySite();

    /**
     * 查询所有模板
     *
     * @return
     */
    @ApiOperation("查询所有模板")
    public QueryResponseResult queryTemplate();

    /**
     * 发布页面
     *
     * @param pageId
     * @return
     */
    @ApiOperation("发布页面")
    public ResponseResult publish(String pageId);

    /**
     * 保存页面
     *
     * @param cmsPage
     * @return
     */
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    /**
     * 页面一键发布
     *
     * @param cmsPage
     * @return
     */
    @ApiOperation("一键发布页面")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);
}
