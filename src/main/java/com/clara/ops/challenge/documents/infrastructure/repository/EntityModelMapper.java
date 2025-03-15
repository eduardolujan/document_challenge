package com.clara.ops.challenge.documents.infrastructure.repository;


import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.DocumentEntity;
import com.clara.ops.challenge.documents.domain.entity.Document;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;


// Infrastructure
import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.TagEntity;

// Domain
import com.clara.ops.challenge.documents.domain.entity.Tag;

@Service
public class EntityModelMapper {

  private final ModelMapper modelMapper;

  public EntityModelMapper() {
    modelMapper = new ModelMapper();
    modelMapper.addMappings(new PropertyMap<TagEntity, Tag>() {
      @Override
      protected void configure() {
        map().setDocumentId(source.getDocumentId());
      }
    });

  }

  public ModelMapper getModelMapper() {
    return modelMapper;
  }
}
