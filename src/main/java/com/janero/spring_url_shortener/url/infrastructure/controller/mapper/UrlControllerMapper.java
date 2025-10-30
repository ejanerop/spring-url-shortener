package com.janero.spring_url_shortener.url.infrastructure.controller.mapper;

import org.mapstruct.Mapper;
import com.janero.spring_url_shortener.shared.infrastructure.config.CustomMapperConfig;
import com.janero.spring_url_shortener.url.domain.model.Url;
import com.janero.spring_url_shortener.url.infrastructure.controller.request.UrlRequest;
import com.janero.spring_url_shortener.url.infrastructure.controller.response.UrlResponse;

@Mapper(config = CustomMapperConfig.class)
public interface UrlControllerMapper {

    Url toModel(UrlRequest request);

    UrlResponse toResponse(Url model);

}
