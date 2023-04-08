package org.dmly.shmiter.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.dir}")
    private String uploadDir;

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDirPath = Path.of(System.getProperty("user.dir"), uploadDir);
        registry.addResourceHandler("/img/**")
                .addResourceLocations(new UrlResource(uploadDirPath.toUri()));
    }
}
