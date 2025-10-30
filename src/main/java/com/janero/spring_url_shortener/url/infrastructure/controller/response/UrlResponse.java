package com.janero.spring_url_shortener.url.infrastructure.controller.response;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UrlResponse {

    private String key;
    private String url;
    private ZonedDateTime createdAt;

}
