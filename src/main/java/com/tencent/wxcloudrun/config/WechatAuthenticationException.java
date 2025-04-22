package com.tencent.wxcloudrun.config;

import org.springframework.security.core.AuthenticationException;

public class WechatAuthenticationException extends AuthenticationException {
    public WechatAuthenticationException(String msg) {
        super(msg);
    }
}
