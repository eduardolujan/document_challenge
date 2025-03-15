package com.clara.ops.challenge.documents.infrastructure.controller;


import com.clara.ops.challenge.documents.application.DocumentLinkGenerator;
import com.clara.ops.challenge.documents.domain.exceptions.CanNotCreateLink;
import com.clara.ops.challenge.documents.domain.exceptions.DocumentNotExists;
import com.clara.ops.challenge.documents.domain.s3.LinkManager;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// Application
import com.clara.ops.challenge.documents.application.DocumentSearcher;

// Domain
import com.clara.ops.challenge.documents.domain.entity.Document;
import com.clara.ops.challenge.documents.domain.repository.TagRepository;
import com.clara.ops.challenge.documents.application.DocumentCreator;
import com.clara.ops.challenge.documents.domain.repository.DocumentRepository;
import com.clara.ops.challenge.documents.domain.s3.FileManager;

@RestController
@RequestMapping("/api/document")
public class DocumentsController {

  private final FileManager fileManager;
  private final LinkManager linkManager;
  private final DocumentRepository documentRepository;
  private final TagRepository tagRepository;
  private final String bucketName;

  public DocumentsController(
      FileManager fileManager,
      LinkManager linkManager,
      DocumentRepository documentRepository,
      TagRepository tagRepository,
      @Value("${minio.bucket-name}") String bucketName) {
    this.fileManager = fileManager;
    this.linkManager = linkManager;
    this.documentRepository = documentRepository;
    this.tagRepository = tagRepository;
    this.bucketName = bucketName;
  }
  @GetMapping("/")
  public ResponseEntity<String> getDocuments() {
    return new ResponseEntity<>("Documents", HttpStatus.OK);
  }

  @PostMapping(name = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseChallenge> uploadDocument(
      @RequestParam("file") MultipartFile file,
      @RequestParam("user") String userName,
      @RequestParam("tags") List<String> tags) {

    try {

      DocumentCreator documentCreator = new DocumentCreator(
        fileManager,
        documentRepository,
        tagRepository,
        bucketName,
        userName,
        file.getOriginalFilename(),
        tags,
        file.getInputStream());
      documentCreator.execute();

    } catch (Exception ex) {
      return new ResponseEntity<>(new ErrorResponse(
          "Error when tried to upload document %s".formatted(ex.getMessage())),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(new OkResponse(true, "Document uploaded"), HttpStatus.OK);
  }

  @GetMapping("/search/{page}/{page_size}/")
  public List<Document> searchDocuments(
      @RequestParam(required = false) String user,
      @RequestParam(required = false) String documentName,
      @RequestParam(required = false) List<String> tags,
      @PathVariable("page") int page,
      @PathVariable("page_size") int pageSize) {

    DocumentSearcher documentSearcher = new DocumentSearcher(documentRepository);
    return documentSearcher.search(user, documentName, tags == null? List.of(): tags, page, pageSize);
  }

  @GetMapping("/download/{id}")
  public ResponseEntity<LinkCreatedResponse> downloadDocument(@PathVariable UUID id) {
    String link = "";
    try {
      DocumentLinkGenerator documentLinkGenerator = new DocumentLinkGenerator(
          documentRepository,
          linkManager);
      link = documentLinkGenerator.generateLink(id, bucketName);

    } catch (DocumentNotExists e) {
      ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    } catch (Exception e){
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
    }
    return ResponseEntity.status(HttpStatus.OK).body(new LinkCreatedResponse(link));
  }

}
