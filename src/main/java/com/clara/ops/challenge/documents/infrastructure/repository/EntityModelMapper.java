package com.clara.ops.challenge.documents.infrastructure.repository;

import com.clara.ops.challenge.bootstrap.infrastructure.db.entities.TagEntity;
import com.clara.ops.challenge.documents.domain.entity.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class EntityModelMapper {

  private final ModelMapper modelMapper;

  public EntityModelMapper() {
    modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<TagEntity, Tag>() {
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
