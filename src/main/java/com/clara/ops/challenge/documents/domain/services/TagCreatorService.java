package com.clara.ops.challenge.documents.domain.services;

import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.entity.Tag;
import com.clara.ops.challenge.documents.domain.exceptions.TagNamesEmpty;
import com.clara.ops.challenge.documents.domain.repository.TagRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TagCreatorService {

  public static void createTags(
      TagRepository tagRepository, Document document, List<String> tagNames) throws TagNamesEmpty {
    if (Objects.isNull(tagNames) || tagNames.isEmpty()) {
      throw new TagNamesEmpty("Tags cannot be null or empty %s".formatted(tagNames));
    }

    for (String tagName : tagNames) {

      Tag tag =
          Tag.builder().id(UUID.randomUUID()).name(tagName).documentId(document.getId()).build();
      tagRepository.save(tag);
    }
  }
}
