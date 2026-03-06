package com.yupathbuilder.backend.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayForceMigrateConfig {

  @Bean
  CommandLineRunner forceFlywayMigrate(DataSource dataSource) {
    return args -> {
      System.out.println(">>> Flyway forced migrate: starting...");
      long start = System.nanoTime();
var result = Flyway.configure()
    .dataSource(dataSource)
    .locations("classpath:db/migration")
    .baselineOnMigrate(true)
    .load()
    .migrate();
long end = System.nanoTime();

System.out.println(">>> Flyway forced migrate: executed=" + result.migrationsExecuted
    + " timeMs=" + ((end - start) / 1_000_000));

      
    };
  }
}