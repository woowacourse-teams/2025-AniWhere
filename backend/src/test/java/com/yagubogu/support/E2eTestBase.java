package com.yagubogu.support;

import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("e2e")
public abstract class E2eTestBase {

    @Container
    protected static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("yagubogu_test_db_mysql")
            .withUsername("yagu")
            .withPassword("bogu");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void migrateOnce(@Autowired Flyway flyway) {
        flyway.migrate(); // DDL 한 번만 실행
    }

    @AfterEach
    void cleanData(final EntityManager em) {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        em.createNativeQuery("TRUNCATE TABLE members").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE teams").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE stadiums").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE refresh_tokens").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE talk_reports").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE check_ins").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE members").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE talks").executeUpdate();

        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}
