package com.shardik.azure.configuration.azure;

import com.microsoft.azure.keyvault.KeyVaultClient;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import static com.shardik.azure.configuration.env.EnvironmentPropertyName.AZURE_KEY_VAULT_APPLICATION_ID;
import static com.shardik.azure.configuration.env.EnvironmentPropertyName.AZURE_KEY_VAULT_CLIENT_SECRET;
import static com.shardik.azure.utils.EnvPropertiesUtils.getRequiredEnvProperty;
import static com.shardik.azure.utils.KeyVaultSecretsReader.readApplicationPropertiesFromAzure;

public class AzureApplicationPropertiesListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        var client = buildAzureIntegrationClient();
        var properties = readApplicationPropertiesFromAzure(client);
        event.getSpringApplication().setDefaultProperties(properties);
    }

    private KeyVaultClient buildAzureIntegrationClient() {
        var applicationId = getRequiredEnvProperty(AZURE_KEY_VAULT_APPLICATION_ID);
        var clientSecret = getRequiredEnvProperty(AZURE_KEY_VAULT_CLIENT_SECRET);
        var azureCreds = new KeyVaultClientCredentials(applicationId, clientSecret);
        return new KeyVaultClient(azureCreds);
    }
}
