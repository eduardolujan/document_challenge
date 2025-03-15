package com.clara.ops.challenge.document_management_service_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.clara"})
@EntityScan(basePackages = {"com.clara.ops.challenge.bootstrap.infrastructure.db.entities"})
@EnableJpaRepositories(basePackages = {"com.clara.ops.challenge.bootstrap.infrastructure.db.repositories"})
public class DocumentManagementServiceChallengeApplication {

  public static void main(String[] args) {
    SpringApplication.run(DocumentManagementServiceChallengeApplication.class, args);
  }
}
