package com.clara.ops.challenge.documents.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.entity.Tag;
import com.clara.ops.challenge.documents.domain.exceptions.TagNamesEmpty;
import com.clara.ops.challenge.documents.domain.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TagCreatorServiceTest {

  @Mock
  private TagRepository tagRepository;

  private Document document;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    document = Document.builder()
        .id(UUID.randomUUID())
        .user("user")
        .documentName("documentName")
        .build();
  }

  @Test
  void createTagsSuccessfully() {
    List<String> tagNames = List.of("tag1", "tag2");

    when(tagRepository.findById(anyString())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> TagCreatorService.createTags(tagRepository, document, tagNames));

    verify(tagRepository, times(2)).save(any(Tag.class));
  }

  @Test
  void createTagsWithExistingTag() {
    List<String> tagNames = List.of("tag1", "tag2");

    when(tagRepository.findById("tag1")).thenReturn(Optional.of(new Tag()));
    when(tagRepository.findById("tag2")).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> TagCreatorService.createTags(tagRepository, document, tagNames));

    verify(tagRepository, times(1)).save(any(Tag.class));
  }

  @Test
  void createTagsWithEmptyTagListThrowsException() {
    List<String> tagNames = List.of();

    assertThrows(TagNamesEmpty.class, () -> TagCreatorService.createTags(tagRepository, document, tagNames));

    verify(tagRepository, never()).save(any(Tag.class));
  }

  @Test
  void createTagsWithNullTagListThrowsException() {
    assertThrows(TagNamesEmpty.class, () -> TagCreatorService.createTags(tagRepository, document, null));

    verify(tagRepository, never()).save(any(Tag.class));
  }
}