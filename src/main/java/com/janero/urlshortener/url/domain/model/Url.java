package com.janero.urlshortener.url.domain.model;

import java.time.ZonedDateTime;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@SuppressWarnings("java:S1700")
public class Url {

    @Id
    private String key;
    private String url;

    private ZonedDateTime createdAt;

}
