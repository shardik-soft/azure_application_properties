package com.shardik.azure.utils;

import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.SecretItem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import static com.shardik.azure.configuration.env.EnvironmentPropertyName.AZURE_KEY_VAULT_BASE_URL;
import static com.shardik.azure.utils.EnvPropertiesUtils.getRequiredEnvProperty;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.replace;

public class KeyVaultSecretsReader {

    private static final String ARTIFICIAL_NAME_SEPARATOR = "--";

    public static Map<String, Object> readApplicationPropertiesFromAzure(KeyVaultClient client) {
        var azureBaseUrl = getRequiredEnvProperty(AZURE_KEY_VAULT_BASE_URL);
        return client.getSecrets(azureBaseUrl).stream()
                .map(SecretItem::id)
                .map(client::getSecret)
                .map(secretBundle -> Pair.of(secretBundle.secretIdentifier().name(), secretBundle.value()))
                .map(pair -> Pair.of(replaceArtificialSeparatorWithPropertiesStyle(pair.getLeft()), pair.getRight()))
                .collect(toMap(Pair::getLeft, Pair::getRight));
    }

    private static String replaceArtificialSeparatorWithPropertiesStyle(String secretName) {
        return replace(secretName, ARTIFICIAL_NAME_SEPARATOR, ".");
    }

}
