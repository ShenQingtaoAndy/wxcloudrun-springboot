package com.tencent.wxcloudrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.ServletContext;
import java.util.Collections;
import javax.faces.webapp.FacesServlet;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.tencent.wxcloudrun.dao")
public class WxCloudRunApplication {  

  public static void main(String[] args) {
    SpringApplication.run(WxCloudRunApplication.class, args);
  }


  @Bean
  public ServletRegistrationBean<FacesServlet> jsfServletRegistration(ServletContext servletContext) {
    // JSF的配置
    servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    // 注册FacesServlet
    ServletRegistrationBean<FacesServlet> srb = new ServletRegistrationBean<>();

    FacesServlet facesServlet = new FacesServlet();
    srb.setServlet(facesServlet);
    srb.setUrlMappings(Collections.singletonList("*.xhtml"));
    srb.setLoadOnStartup(1);
    return srb;
  }
}
