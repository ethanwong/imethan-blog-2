package com.imethan.blog.service;

import com.imethan.blog.dto.ResultDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @Name AutoDeployService
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-07 13:59
 */
@Service
@Log4j2
public class AutoDeployService {

    @Value("classpath:deploy.sh")
    private Resource resource;


    public ResultDto execShell() {
        boolean flag = true;
        String message = "";
        try {
            String dir = "/home";

            File shellFile = resource.getFile();

            ProcessBuilder processBuilder = new ProcessBuilder(shellFile.getPath());
            processBuilder.directory(new File(dir));
            Process process = processBuilder.start();
            String input;
            String error;
            BufferedReader bufferedReaderInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader bufferedReaderErrorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((input = bufferedReaderInputStream.readLine()) != null) {
                log.info("execShell InputStream input={}", input);
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
            return ResultDto.ReturnFail("");
        }
        return ResultDto.ReturnSuccess();
    }
}
