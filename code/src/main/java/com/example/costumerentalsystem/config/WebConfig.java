package com.example.costumerentalsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminAccessInterceptor adminAccessInterceptor;

    public WebConfig(AdminAccessInterceptor adminAccessInterceptor) {
        this.adminAccessInterceptor = adminAccessInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // รูปที่ commit ไว้ใน repo อยู่ใน classpath (ในไฟล์ jar)
        // รูปที่เพิ่งอัปโหลดตอนรันอยู่ในโฟลเดอร์ข้างนอก jar ต้องชี้ไปอ่านด้วย ไม่งั้นรูปไม่ขึ้น
        // (path แบบ relative นับจากโฟลเดอร์ที่รันแอป ตรงกับที่ controller เขียนไฟล์ไว้)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(
                        "classpath:/static/uploads/",
                        "file:src/main/resources/static/uploads/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAccessInterceptor)
                .addPathPatterns("/admin/**",
                        "/costumes/admin/**", "/costumes/edit/**",
                        "/costumes/update/**", "/costumes/delete/**",
                        "/api/v1/shipments/**");
    }
}
