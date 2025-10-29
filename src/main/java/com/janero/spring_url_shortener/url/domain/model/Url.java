package com.janero.spring_url_shortener.url.domain.model;

import java.time.ZonedDateTime;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@SuppressWarnings("java:S1700")
public class Url {

    @Id
    private String key;
    private String url;

    private ZonedDateTime createdAt;

}
