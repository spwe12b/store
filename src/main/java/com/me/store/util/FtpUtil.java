package com.me.store.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * FTP文件上传工具类
 */
public class FtpUtil {
    private static final Logger logger= LoggerFactory.getLogger(FtpUtil.class);
    private static String ftpIp=PropertiesUtil.getProperty("ftp.server.ip");
    private static int ftpPort=Integer.valueOf(PropertiesUtil.getProperty("ftp.server.port"));
    private static String ftpUser=PropertiesUtil.getProperty("ftp.user");
    private static String ftpPassword=PropertiesUtil.getProperty("ftp.password");

    public static boolean uploadFile(List<File> fileList)throws IOException{
        FtpUtil ftpUtil=new FtpUtil(ftpIp,ftpPort,ftpUser,ftpPassword);
        logger.info("开始连接FTP服务器");
        boolean result=ftpUtil.uploadFile("img",fileList);
        logger.info("结束上传,上传结果{}",result);
        return result;
    }

    //得先创建FTP共享目录
    /**
     * 上传文件到FTP服务器
     * @param remotePath
     * @param fileList
     * @return
     * @throws IOException
     */
    private boolean uploadFile(String remotePath,List<File> fileList)throws IOException{
        boolean uploaded=false;
        FileInputStream fileInputStream=null;
        if(connectServer(this.ip,this.port,this.user,this.password)){
           try{
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            for(File file:fileList){
                fileInputStream=new FileInputStream(file);
                ftpClient.storeFile(file.getName(),fileInputStream);
            }
            uploaded=true;
        }catch (IOException e){
               logger.error("上传文件到ftp服务器异常",e);
           }finally {
               fileInputStream.close();
               ftpClient.disconnect();
           }
        }
        return uploaded;
    }

    /**
     * 连接FTP服务器
     * @param ip
     * @param port
     * @param user
     * @param password
     * @return
     */
    private boolean connectServer(String ip,int port,String user,String password){
        boolean isSuccess=false;
        ftpClient=new FTPClient();
            try {
            ftpClient.connect(ip,port);
            isSuccess = ftpClient.login(user,password);
        } catch (IOException e) {
            logger.error("连接ftp服务器异常",e);
        }
        return isSuccess;
    }

    public FtpUtil(String ip, int port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password=password;
    }

    private String ip;
    private int port;
    private String user;
    private String password;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
