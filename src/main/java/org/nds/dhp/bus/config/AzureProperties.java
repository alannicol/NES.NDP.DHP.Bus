package org.nds.dhp.bus.config;

import org.nds.dhp.bus.config.validation.ResolvedEnvironmentVariable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Validated
@ConstructorBinding
@ConfigurationProperties("azure")
public class AzureProperties {
  private final ServiceBus serviceBus;

  public AzureProperties(ServiceBus serviceBus) {
    this.serviceBus = serviceBus;
  }

  public ServiceBus getServiceBus() {
    return serviceBus;
  }

  public static class ServiceBus {

    @ResolvedEnvironmentVariable
    private final String connectionString;

    public ServiceBus(String connectionString) {
      this.connectionString = connectionString;
    }

    public String getConnectionString() {
      return connectionString;
    }
  }
}
