package com.tencent.wxcloudrun.config;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

@Configuration
public class UploadFilter {



    @Bean
    public FilterRegistrationBean<FileUploadFilter> fileUploadFilterFilterRegistration(ServletContext servletContext){
        FilterRegistrationBean<FileUploadFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addUrlPatterns("/pages/*");
        FileUploadFilter fileUploadFilter = new FileUploadFilter();
        servletContext.setInitParameter("primefaces.UPLOADER","commons");
        registrationBean.setFilter(fileUploadFilter);
        return registrationBean;
    }

}
