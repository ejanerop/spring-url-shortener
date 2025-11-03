package com.janero.urlshortener.url.infrastructure.controller.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UrlResponse {

    private String key;
    private String url;

}
