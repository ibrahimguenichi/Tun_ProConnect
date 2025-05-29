package com.ProConnect.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app") // Matches properties like app.database.host
@Getter
@Setter
public class ApplicationProperties {
    private final DatabaseConfiguration database;
    private List<String> allowedOrigins;
    private String baseUrl;
    private String applicationName;
    private String loginPageUrl;
    private String loginSuccessUrl;
    private String adminUserEmail;
    private String adminUserPassword;
    private String vapidPublicKey;
    private String vapidPrivateKey;
    private String vapidSubject;

    public ApplicationProperties() {
        this.database = new DatabaseConfiguration();
    }
}
