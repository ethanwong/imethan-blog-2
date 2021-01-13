package com.imethan.blog.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import java.io.File;
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
            log.info("process error={}", IOUtils.toString(errorStream, "UTF-8"));
            if (input != null) {
                input.close();
                if (errorStream != null) {
                    errorStream.close();
                }
            }
            if (process.waitFor() == 0) {
                log.info("process success command={}", command);
            } else {
                log.info("process fail command={}", command);
            }
        } catch (Exception e) {
            log.error("execCommand error ", e);
        }

    }

    /**
     * 导出数据库指定数据集
     *
     * @param database
     * @param collection
     * @return
     */
    private static String exportCommand(String database, String collection, String targetBackupDir) {
        //mongoexport -d imethan_blog_3 -c blog_article -o /home/mongodb-bak/20210111/blog_article.json --type=json
        String fileFullName = targetBackupDir + File.separator + collection + ".json";
        StringBuffer command = new StringBuffer();
        command.append("mongoexport ");
        command.append("-d " + database + " ");
        command.append("-c " + collection + " ");
        command.append("-o " + fileFullName);
        command.append(" --type=json");
        String commandString = command.toString();
        log.info("command={}", commandString);
        return commandString;
    }

    /**
     * 执行数据集合备份
     *
     * @param database
     * @param collection
     * @param targetBackupDir
     */
    private static void exportCollection(String database, String collection, String targetBackupDir) {
        //生成命令
        String command = exportCommand(database, collection, targetBackupDir);
        //执行命令
        execCommand(command);

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
        String targetBackupDir = MONGODB_EXPORT_DIR + File.separator + date;

        for (String collection : collections) {
            exportCollection(database, collection, targetBackupDir);
        }

        log.info("mongodb back target dir={}", targetBackupDir);

        //压缩备份文件，并且推送
        String targetFileFullName = MONGODB_EXPORT_DIR + "/" + date + "/" + "imethan-blog-2-" + date + ".tar.gz";
        String command = "tar -zcvf " + targetFileFullName + " " + targetBackupDir + " --warning=no-file-changed";
        log.info("package backup file command={}", command);
        execCommand(command);
        log.warn("targetFileFullName={}", targetFileFullName);
        return targetFileFullName;

    }


}
