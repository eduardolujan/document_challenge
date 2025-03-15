package com.clara.ops.challenge.documents.domain.repository;

import com.clara.ops.challenge.documents.domain.entity.Tag;
import java.util.Optional;

public interface TagRepository {
  Optional<Tag> findById(String name);

  Tag save(Tag tag);
}
