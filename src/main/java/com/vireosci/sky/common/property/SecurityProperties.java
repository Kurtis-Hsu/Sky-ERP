package com.vireosci.sky.common.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "sky.security")
public record SecurityProperties(
        @NotNull(message = "{sky.security.key.blank}")
        @NotBlank(message = "{sky.security.key.blank}")
        String key
) { }
