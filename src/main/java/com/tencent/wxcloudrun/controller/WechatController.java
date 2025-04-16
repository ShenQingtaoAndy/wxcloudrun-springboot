package com.tencent.wxcloudrun.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class WechatController {


    private static final String APP_ID = "wx39fea4d428f90a64";
    private static final String SECRET = "fb9912d4767a68f4909d9eeea619fca1";

    @GetMapping("/getOpenId")
    public String getOpenId(HttpServletRequest request) throws WxErrorException {
        // 从请求头或者参数中获取code（微信授权码）
        String code = request.getParameter("code");
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(APP_ID);
        config.setSecret(SECRET);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        // 通过code换取用户的OpenId等信息
        String openId = wxMpService.getOAuth2Service().getAccessToken(code).getOpenId();
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

        WxMpService wxMpService = new WxMpServiceImpl();
        // 创建微信公众号配置对象
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        // 设置微信公众号的 appId
        config.setAppId("wx39fea4d428f90a64");
        // 设置微信公众号的 appSecret
        config.setSecret("fb9912d4767a68f4909d9eeea619fca1");
        // 设置微信公众号的 token
        config.setToken("yourToken");
        // 设置微信公众号的 aesKey
        config.setAesKey("yourAesKey");
        wxMpService.setWxMpConfigStorage(config);
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }
}
