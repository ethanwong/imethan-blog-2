package com.imethan.blog.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtils {

    /**
     * 保存图片
     * @param uploadFile
     * @return
     */
    public static String saveImage(MultipartFile uploadFile) {
        try {
            //获取文件名
            String fileName = uploadFile.getOriginalFilename();
            //文件后缀
            String extension = FilenameUtils.getExtension(fileName);
            //根路径
            String rootPath = "D:\\";
            //相对路径
            String relativePath = genRelativePath("upload/image", extension);
            File destFile = new File(rootPath + relativePath);
            //父级目录不存在，则创建
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            //保存文件
            uploadFile.transferTo(destFile);
            return relativePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存文件
     * @param uploadFile
     * @return
     */
    public static String saveFile(MultipartFile uploadFile) {
        try {
            //获取文件名
            String fileName = uploadFile.getOriginalFilename();
            //文件后缀
            String extension = FilenameUtils.getExtension(fileName);
            //根路径
            String rootPath = "D:\\";
            //相对路径
            String relativePath = genRelativePath("upload/file", extension);
            File destFile = new File(rootPath + relativePath);
            //父级目录不存在，则创建
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            //保存文件
            uploadFile.transferTo(destFile);
            return relativePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成文件相对路径
     * @param dir
     * @param extension
     * @return
     */
    public static String genRelativePath(String dir, String extension) {
        StringBuffer relativePath = new StringBuffer();
        dir = StringUtils.defaultString(dir, "upload");
        //设置目录
        relativePath.append("/").append(dir).append(
                new SimpleDateFormat("/yyyyMM/dd/").format(new Date()));
        //UUID文件名
        relativePath.append(UUID.randomUUID());
        //若有后缀则添加
        if (StringUtils.isNotBlank(extension)) {
            relativePath.append(".").append(extension);
        }
        return relativePath.toString();
    }
}
