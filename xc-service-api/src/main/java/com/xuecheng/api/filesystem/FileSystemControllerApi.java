package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 蔡闯王
 * @date 2019/2/6 20:52
 */
@Api(value = "文件上传管理接口", description = "文件的上传下载")
public interface FileSystemControllerApi {
    /**
     * 上传文件
     *
     * @param file
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    public UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata);
}
