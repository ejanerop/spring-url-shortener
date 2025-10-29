package com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.mapper;

import org.mapstruct.Mapper;
import com.janero.spring_url_shortener.shared.infrastructure.config.CustomMapperConfig;
import com.janero.spring_url_shortener.url.domain.model.Url;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.entity.UrlEntity;

@Mapper(config = CustomMapperConfig.class)
public interface UrlEntityMapper {

    Url toModel(UrlEntity entity);

    UrlEntity toEntity(Url model);

}
