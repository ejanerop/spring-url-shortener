package com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.entity;

import java.time.ZonedDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "url")
public class UrlEntity {

    @Id
    private String key;
    private String url;

    private ZonedDateTime createdAt;

}
