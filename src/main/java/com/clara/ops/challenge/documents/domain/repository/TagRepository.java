package com.clara.ops.challenge.documents.domain.repository;


import java.util.Optional;

// Domain
import com.clara.ops.challenge.documents.domain.entity.Tag;

public interface TagRepository {
  Optional<Tag> findById(String name);
  Tag save(Tag tag);
}
