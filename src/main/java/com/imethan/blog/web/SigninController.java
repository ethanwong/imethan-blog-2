package com.imethan.blog.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

import javax.imageio.ImageIO;
import javax.security.auth.Subject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @Name LoginController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 10:27
 */
@Controller
@Log4j2
public class SigninController {

    @GetMapping(value = "/signin")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "signin";
    }

    @GetMapping(value = "/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/validCode")
    public void validCode(HttpServletResponse response, HttpSession httpSession){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = drawImg(output);

        httpSession.setAttribute("valid_code", code);

        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("image/jpeg");
            output.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 绘画验证码
     * @param output
     * @return
     */
    private String drawImg(ByteArrayOutputStream output){
        String code = "";
        //随机产生4个字符
        for(int i=0; i<4; i++){
            code += randomChar();
        }
        int width = 80;
        int height = 39;
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN,20);
        //调用Graphics2D绘画验证码
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66,2,82);
        g.setColor(color);
        g.setBackground(new Color(226,226,240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int)x, (int)baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 随机参数一个字符
     * @return
     */
    private char randomChar(){
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
}
