package com.imethan.blog.service;

import com.imethan.blog.pojo.dto.ResultDto;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name AutoDeployService
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-07 13:59
 */
@Service
@Log4j2
public class AutoDeployService {

    private final String dir = "/home";
    private final String fileName = "imethan-deploy.sh";
    private final String fileFullName = dir + File.separator + fileName;

    @Value("classpath:deploy.sh")
    private Resource resource;

    /**
     * 设置自动部署脚本
     */
    public void setShell() {
        log.info("AutoDeployService setShell fileFullName={}", fileFullName);
        try {
            File file = new File(fileFullName);
            if (file.exists()) {
                file.delete();
            }
            FileWriter fileWriter = new FileWriter(fileFullName);
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String data;
            while ((data = bufferedReader.readLine()) != null) {
                fileWriter.write(data + "\n");
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            fileWriter.close();

            // 设置权限，否则会报 Permission denied
            file.setReadable(true);
            file.setWritable(true);
            file.setExecutable(true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void exec() {
        ResultDto resultDto = this.execShell("start", "");
        Map<String, String> data = (Map<String, String>) resultDto.getData();
        String pids = data.get("pids");

        log.info("exec start pids={}", pids);
        log.info("exec stop pids={}", pids);
        this.execShell("stop", pids);
    }


    public ResultDto execShell(String command, String parameter) {
        boolean flag = true;
        String message = "";

        String pids = "";
        try {
            ProcessBuilder processBuilder = null;
            if (StringUtils.isNoneBlank(parameter)) {
                processBuilder = new ProcessBuilder(fileFullName, command, parameter);
            } else {
                processBuilder = new ProcessBuilder(fileFullName, command);
            }
            processBuilder.directory(new File(dir));
            Process process = processBuilder.start();
            String input;
            String error;
            BufferedReader bufferedReaderInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader bufferedReaderErrorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((input = bufferedReaderInputStream.readLine()) != null) {
                log.info("execShell InputStream input={}", input);
                if (input.contains("pids")) {
                    pids = input.substring(input.indexOf("="));
                }
            }
            while ((error = bufferedReaderErrorStream.readLine()) != null) {
                log.info("execShell InputStream error={}", error);
            }

            int runningStatus = 0;
            try {
                runningStatus = process.waitFor();
            } catch (InterruptedException e) {
                log.error("execShell", e);
                flag = false;
                message = e.getMessage();
            }

            if (runningStatus != 0) {
                log.error("execShell failed.");
                flag = false;
                message = "execShell failed.";
            } else {
                log.info("execShell success.");

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            flag = false;
            message = e.getMessage();
        }
        if (!flag) {
            return ResultDto.ReturnFail(message);
        }
        Map<String, String> data = new HashMap<>();
        data.put("pids", pids);
        return ResultDto.ReturnSuccessData(data);
    }


}
