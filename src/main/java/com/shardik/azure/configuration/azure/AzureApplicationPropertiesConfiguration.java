package com.shardik.azure.configuration.azure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureApplicationPropertiesConfiguration {

    @Bean
    public AzureApplicationPropertiesListener azureApplicationPropertiesListener() {
        return new AzureApplicationPropertiesListener();
    }
}
