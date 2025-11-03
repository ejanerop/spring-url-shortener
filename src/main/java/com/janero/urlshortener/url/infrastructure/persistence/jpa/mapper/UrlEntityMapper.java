package com.janero.urlshortener.url.infrastructure.persistence.jpa.mapper;

import org.mapstruct.Mapper;
import com.janero.urlshortener.shared.infrastructure.config.CustomMapperConfig;
import com.janero.urlshortener.url.domain.model.Url;
import com.janero.urlshortener.url.infrastructure.persistence.jpa.entity.UrlEntity;

@Mapper(config = CustomMapperConfig.class)
public interface UrlEntityMapper {

    Url toModel(UrlEntity entity);

    UrlEntity toEntity(Url model);

}
