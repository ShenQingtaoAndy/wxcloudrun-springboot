package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.CustomUserDetailsService;
import com.tencent.wxcloudrun.config.WechatAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.logging.log4j.util.Strings;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
public class WechatController {


    @Autowired
    private WxMpService weixinMpService;

    @GetMapping("/api/getOpenId")
    public String getOpenId(HttpServletRequest request) throws WxErrorException {
        // 从请求头或者参数中获取code（微信授权码）
        String code = request.getParameter("code");
        // 通过code换取用户的OpenId等信息
        String openId = weixinMpService.getOAuth2Service().getAccessToken(code).getOpenId();
        log.info("openId: {}", openId);
        return openId;
    }


    @ResponseBody
    @GetMapping(value = "/api/wechat/core", produces = "text/plain;charset=utf-8")
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (Strings.isEmpty(signature) || Strings.isEmpty(timestamp)
                || Strings.isEmpty(nonce) || Strings.isEmpty(echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (weixinMpService.checkSignature(timestamp, nonce, signature)) {
            log.info("echostr:{}", echostr);
            return echostr;
        }

        return "非法请求";
    }
}
