package com.zzyl.common.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "baidu.qianfan")
public class BaiduAIProperties {
    private String apiKey;
    private String baseUrl;
    private String model;
}