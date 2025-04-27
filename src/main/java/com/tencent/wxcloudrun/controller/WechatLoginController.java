package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class WechatLoginController {


    @Autowired
    private WxMpService weixinMpService;


    @Autowired
    private CustomUserDetailsService userDetailsService;


    @GetMapping("/loginByWechat")
    public String getOpenId(@RequestParam(name = "code", required = false) String code,
                            @RequestParam(name = "redirecturl", required = false ,defaultValue = "") String redirectUrl,
                            @RequestParam(name = "openid", required = false) String openId) throws WxErrorException {

        if (Strings.isEmpty(code) && Strings.isEmpty(openId)) {
            return "/";
        }

        if(!Strings.isEmpty(code)){
            openId = weixinMpService.getOAuth2Service().getAccessToken(code).getOpenId();
        }
        log.info("openId: {}", openId);
        if (Strings.isBlank(openId)) {
            return "/";
        }
        UserDetails userDetails = userDetailsService.loadUserByOpenId(openId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return redirectUrl;

    }
}
