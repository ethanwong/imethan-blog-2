package com.imethan.blog.controller.web;

import com.google.code.kaptcha.Producer;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Log4j2
@RestController
public class KaptchaController {

    @Resource(name = "kaptchaProducer")
    private Producer kaptchaProducer;

    @Resource(name = "kaptchaProducerMath")
    private Producer kaptchaProducerMath;

    private String kaptchaType = "math";


    @GetMapping("/kaptchaCode")
    public void getCode(HttpServletResponse response, HttpSession httpSession) throws IOException {

        String capStr = null;
        String code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(kaptchaType)) {
            String capText = kaptchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = kaptchaProducerMath.createImage(capStr);
        } else if ("char".equals(kaptchaType)) {
            capStr = code = kaptchaProducer.createText();
            image = kaptchaProducer.createImage(capStr);
        }

        httpSession.setAttribute("kaptcha_code", code);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            log.error("kaptchaCode error", e);
        }

        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("image/jpeg");
            os.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
