package com.wfs.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class SecurityProperties {
    private String keystoreLocation;
    private String keystoreFile;
    private String keystorePassword;
    private String keyAlias;
    private String privateKeyPassphrase;
}
