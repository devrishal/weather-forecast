package com.wfs.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix = "application.user")
public class UserProperties {
    private List<String> username;
    private List<String> password;
    private List<String> roles;


}
