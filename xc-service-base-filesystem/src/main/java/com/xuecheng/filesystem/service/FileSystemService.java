package com.xuecheng.filesystem.service;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.model.response.CommonCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 蔡闯王
 * @date 2019/2/6 20:55
 */
@Service
public class FileSystemService {

    public UploadFileResult upload(MultipartFile file, String filetag, String metadata) {
        //创建文件信息对象
        FileSystem fileSystem = new FileSystem();
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }
}
