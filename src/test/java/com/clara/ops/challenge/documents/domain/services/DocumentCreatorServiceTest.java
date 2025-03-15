package com.clara.ops.challenge.documents.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.exceptions.DocumentParamEmpty;
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DocumentCreatorServiceTest {

  @Mock private DocumentRepository documentRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createDocumentSuccessfully() throws DocumentParamEmpty {
    String user = "user";
    String documentName = "documentName";

    Document document =
        DocumentCreatorService.createDocument(documentRepository, user, documentName);

    assertNotNull(document);
    assertEquals(user, document.getUser());
    assertEquals(documentName, document.getDocumentName());
    verify(documentRepository, times(1)).save(document);
  }

  @Test
  void createDocumentWithNullUserThrowsException() {
    String documentName = "documentName";

    assertThrows(
        DocumentParamEmpty.class,
        () -> {
          DocumentCreatorService.createDocument(documentRepository, null, documentName);
        });

    verify(documentRepository, never()).save(any(Document.class));
  }

  @Test
  void createDocumentWithNullDocumentNameThrowsException() {
    String user = "user";

    assertThrows(
        DocumentParamEmpty.class,
        () -> {
          DocumentCreatorService.createDocument(documentRepository, user, null);
        });

    verify(documentRepository, never()).save(any(Document.class));
  }

  @Test
  void createDocumentWithEmptyUserThrowsException() {
    String user = "";
    String documentName = "documentName";

    assertThrows(
        DocumentParamEmpty.class,
        () -> {
          DocumentCreatorService.createDocument(documentRepository, user, documentName);
        });

    verify(documentRepository, never()).save(any(Document.class));
  }

  @Test
  void createDocumentWithEmptyDocumentNameThrowsException() {
    String user = "user";
    String documentName = "";

    assertThrows(
        DocumentParamEmpty.class,
        () -> {
          DocumentCreatorService.createDocument(documentRepository, user, documentName);
        });

    verify(documentRepository, never()).save(any(Document.class));
  }
}
