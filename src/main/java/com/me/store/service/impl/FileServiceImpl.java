package com.me.store.service.impl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.me.store.common.ServerResponse;
import com.me.store.service.IFileService;
import com.me.store.util.FtpUtil;
import com.me.store.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * FTP上传文件
     * @param multipartFile
     * @param path
     * @return
     */
    public ServerResponse<Map> upload(MultipartFile multipartFile, String path) {
        //获取原始文件名
        String fileName = multipartFile.getOriginalFilename();
        //获取上传文件的扩展名(.XXX)
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString() + fileExtensionName;
        logger.info("开始上传到tomcat，上传文件名{}，上传路径{}，新文件名{}", fileName, path, uploadFileName);
        //查看该path下的目录是否存在
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);//可写的权限
            fileDir.mkdirs();//创建目录
        }
        //tomcat目标文件
        File targetFile = new File(path, uploadFileName);
        try {
            //上传到tomcat目录
            multipartFile.transferTo(targetFile);
            targetFile.setWritable(true);
            //上传到FTP服务器上
            if (FtpUtil.uploadFile(Lists.newArrayList(targetFile))) {
                String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
                Map map = Maps.newHashMap();
                map.put("url", url);
                map.put("uri", targetFile.getName());
                //删除tomcat目下的文件
                targetFile.delete();
                return ServerResponse.createBySuccess(map, "上传文件成功");
            }
            //删除tomcat目下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return ServerResponse.createByErrorMessage("上传文件失败");
        }
        return ServerResponse.createByErrorMessage("上传文件失败");
    }
}