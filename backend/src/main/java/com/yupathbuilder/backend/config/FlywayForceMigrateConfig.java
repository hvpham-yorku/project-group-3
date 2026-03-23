package com.yupathbuilder.backend.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!stub")
public class FlywayForceMigrateConfig {

  @Bean
  CommandLineRunner forceFlywayMigrate(
      DataSource dataSource,
      @Value("${app.flyway.repair-on-startup:false}") boolean repairOnStartup) {
    return args -> {
      System.out.println(">>> Flyway forced migrate: starting...");
      long start = System.nanoTime();

      Flyway flyway = Flyway.configure()
          .dataSource(dataSource)
          .locations("classpath:db/migration")
          .baselineOnMigrate(true)
          .validateOnMigrate(false)
          .load();

      if (repairOnStartup) {
        System.out.println(">>> Flyway forced repair: starting...");
        flyway.repair();
      }

      var result = flyway.migrate();

      long end = System.nanoTime();

      System.out.println(">>> Flyway forced migrate: executed=" + result.migrationsExecuted
          + " timeMs=" + ((end - start) / 1_000_000));
    };
  }
}
