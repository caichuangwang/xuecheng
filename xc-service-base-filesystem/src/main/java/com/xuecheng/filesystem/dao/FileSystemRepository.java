package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 蔡闯王
 * @date 2019/2/6 20:54
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {
}
