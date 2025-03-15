package com.clara.ops.challenge.documents.infrastructure.repository;


import java.util.Optional;
import org.springframework.stereotype.Repository;

// Infrastructure
import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.TagEntity;
import com.clara.ops.challenge.bootstrap.infrastructure.db.repositories.JpaTagRepository;

// Domain
import com.clara.ops.challenge.documents.domain.entity.Tag;
import com.clara.ops.challenge.documents.domain.repository.TagRepository;


@Repository
public class SpringTagRepository implements TagRepository {

  private final JpaTagRepository jpaTagRepository;
  private final EntityModelMapper entityModelMapper;

  public SpringTagRepository(JpaTagRepository jpaTagRepository, EntityModelMapper entityModelMapper) {
    this.jpaTagRepository = jpaTagRepository;
    this.entityModelMapper = entityModelMapper;
  }

  @Override
  public Optional<Tag> findById(String name) {
    return jpaTagRepository.findByName(name).map(tagEntity -> entityModelMapper.getModelMapper().map(tagEntity, Tag.class));
  }

  @Override
  public Tag save(Tag tag) {
    jpaTagRepository.save(entityModelMapper.getModelMapper().map(tag, TagEntity.class));
    return tag;
  }
}
