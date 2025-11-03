package com.janero.urlshortener.url.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import com.janero.urlshortener.shared.infrastructure.config.CustomMapperConfig;
import com.janero.urlshortener.url.domain.model.Url;
import com.janero.urlshortener.url.infrastructure.controller.request.UrlRequest;
import com.janero.urlshortener.url.infrastructure.controller.response.UrlResponse;

@Mapper(config = CustomMapperConfig.class)
public interface UrlControllerMapper {

    Url toModel(UrlRequest request);

    UrlResponse toResponse(Url model);

}
