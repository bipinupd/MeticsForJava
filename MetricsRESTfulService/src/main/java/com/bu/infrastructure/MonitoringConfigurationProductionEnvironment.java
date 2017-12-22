package com.bu.infrastructure;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;

@Configuration
@EnableMetrics
@Profile({ "prod" })
public class MonitoringConfigurationProductionEnvironment extends AbstractMonitoringConfiguration {

  private static final String GRAPHITE_PREFIX = "java-app-metrics";

  @Override
  public void configureReporters() {
    configureReporters(GRAPHITE_PREFIX);
  }
  
  @PostConstruct()
  public void init() {
    configureReporters();
  }
}