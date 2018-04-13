package com.example.eurekaclient.controller;

import com.example.eurekaclient.model.ApiResponse;
import com.example.eurekaclient.utils.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description="Spring Boot Mail", tags = "Mail API")
@ConditionalOnWebApplication
@RestController
@RequestMapping("/mail")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmailController extends BaseController {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("{$spring.mail.username}")
    public String from;

    /**
     * 普通文本邮件发送
     * @param to
     * @param subject
     * @param text
     * @return
     */
    @ApiOperation(value="普通文本邮件发送")
    @PutMapping(value = "/service/textSent")
    public ApiResponse insert(@RequestParam String to, @RequestParam String subject, @RequestParam String text)  {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(to);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
        return super.callback(true);
    }

}
