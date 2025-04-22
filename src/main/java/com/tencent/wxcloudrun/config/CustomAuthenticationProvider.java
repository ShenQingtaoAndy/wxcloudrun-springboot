package com.tencent.wxcloudrun.config;


import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    private final WxMpService weixinMpService;

    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService, WxMpService weixinMpService) {
        this.userDetailsService = userDetailsService;
        this.weixinMpService = weixinMpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String code = request.getParameter("code");

        UserDetails userDetails;
        if (Strings.isBlank(code)) {
            String username = authentication.getName();
            userDetails = userDetailsService.loadUserByUsername(username);
        }else{
            String openId = getOpenId(code);
            userDetails = userDetailsService.loadUserByOpenId(openId);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    public String getOpenId(String code) throws AuthenticationException{

        // 通过code换取用户的OpenId等信息

        String newOpenId = null;
        try {
            newOpenId = weixinMpService.getOAuth2Service().getAccessToken(code).getOpenId();
        } catch (WxErrorException e) {
            throw new WechatAuthenticationException(e.getMessage());
        }
        log.info("openId:{}", newOpenId);
        return newOpenId;
    }
}