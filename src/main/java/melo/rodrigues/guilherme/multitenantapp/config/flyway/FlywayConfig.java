package melo.rodrigues.guilherme.multitenantapp.config.flyway;

import melo.rodrigues.guilherme.multitenantapp.config.hibernate.tenant.TenantIdentifierResolver;
import melo.rodrigues.guilherme.multitenantapp.repositories.UserRepository;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
            .locations("db/migration/default")
            .dataSource(dataSource)
            .schemas(TenantIdentifierResolver.DEFAULT_TENANT)
            .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository repository, DataSource dataSource) {
        return args -> {
            repository.findAll().forEach(user -> {
                String tenant = user.getUsername();
                Flyway flyway = Flyway.configure()
                    .locations("db/migration/tenants")
                    .dataSource(dataSource)
                    .schemas(tenant)
                    .load();
                flyway.migrate();
            });
        };
    }
}
