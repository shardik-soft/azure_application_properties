package com.shardik.azure.utils;

import com.shardik.azure.configuration.env.EnvironmentPropertyName;

import static java.lang.String.format;
import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

public class EnvPropertiesUtils {

    public static String getRequiredEnvProperty(EnvironmentPropertyName property) {
        var value = ofNullable(getenv(property.name()));
        return value.orElseThrow(() -> new IllegalStateException(format("Required environment property %s is missing." +
                "Please read the manual to configure the app.", property)));
    }
}
