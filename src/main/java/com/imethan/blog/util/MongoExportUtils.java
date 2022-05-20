package com.imethan.blog.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Name MongoBakUtils
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-11 17:45
 */
@Log4j2
public class MongoExportUtils {

    /**
     * Linux服务上，MongoDB备份路径
     */
    private static final String MONGODB_EXPORT_DIR = "/home/mongodb-export";
    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "imethan_blog_2";

    /**
     * 执行命令
     *
     * @param command
     */
    private static void execCommand(String command) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);

            InputStream input = process.getInputStream();
            log.info("process input={}", IOUtils.toString(input, "UTF-8"));

            InputStream errorStream = process.getErrorStream();

            String errorMsg = IOUtils.toString(errorStream, "UTF-8");
            if (StringUtils.isNoneBlank(errorMsg)) {
                log.info("process error={}", errorMsg);
            }

            if (input != null) {
                input.close();
                if (errorStream != null) {
                    errorStream.close();
                }
            }
            process.waitFor();
            log.info("process wait for over");
        } catch (Exception e) {
            log.error("execCommand error ", e);
        }

    }


    /**
     * 执行数据集合备份
     *
     * @param database
     * @param collection
     * @param targetBackupDir
     * @return
     */
    private static String exportCollection(String database, String collection, String targetBackupDir) {

        //mongoexport -d imethan_blog_3 -c blog_article -o /home/mongodb-bak/20210111/blog_article.json --type=json
        String fileFullName = targetBackupDir + File.separator + collection + ".json";
        StringBuffer command = new StringBuffer();
        command.append("mongoexport ");
        command.append("-d " + database + " ");
        command.append("-c " + collection + " ");
        command.append("-o " + fileFullName);
        command.append(" --type=json");


        //执行命令
        String commandString = command.toString();
        log.info("command={}", commandString);
        execCommand(commandString);

        //生成md5值
        String md5 = "";
        try {
            md5 = DigestUtils.md5DigestAsHex(new FileInputStream(fileFullName));
            log.info("fileFullName=" + fileFullName + ",md5=" + md5);
        } catch (Exception e) {
            log.error(e);
        }
        return md5;
    }

    /**
     * 备份mongodb所有集合
     *
     * @param database
     * @param collections
     */
    public static String exportAll(String database, List<String> collections) {
        //当前日期：yyyyMMdd
        String date = TimeUtils.dateToString(new Date(), TimeUtils.DATETIME_FORMAT_02);

        //备份文件存储路径
        String targetBackupDir = MONGODB_EXPORT_DIR + File.separator + date + File.separator + DATABASE_NAME;

        for (String collection : collections) {
            exportCollection(database, collection, targetBackupDir);
        }

        log.info("mongodb back target dir={}", targetBackupDir);

        //压缩备份文件，并且推送
        String targetFileFullName = MONGODB_EXPORT_DIR + "/" + "imethan-blog-2-db-" + date + ".tar.gz";
        String command = "tar -zcvPf " + targetFileFullName + " " + targetBackupDir + " --warning=no-file-changed";
        log.info("package backup file command={}", command);
        execCommand(command);
        log.warn("targetFileFullName={}", targetFileFullName);
        return targetFileFullName;

    }


}
