package com.clara.ops.challenge.bootstrap.infrastructure.db.repositories;


import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

// Infrastructure
import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.TagEntity;


public interface JpaTagRepository extends JpaRepository<TagEntity, UUID> {
  Optional<TagEntity> findByName(String name);
}
