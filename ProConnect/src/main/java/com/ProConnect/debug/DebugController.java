package com.ProConnect.debug;

import com.ProConnect.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/debug")
@RequiredArgsConstructor
public class DebugController {

    private final ApplicationProperties applicationProperties;

    @GetMapping("config")
    public String checkConfig() {
        return "DB Name: " + applicationProperties.getDatabase().getName();
    }
}
