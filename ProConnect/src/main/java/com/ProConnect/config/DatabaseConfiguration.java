package com.ProConnect.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConfiguration {
    private String name;
    private String username;
    private String password;
    private String host;
    private int port;
}
