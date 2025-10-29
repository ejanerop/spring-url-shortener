package com.janero.spring_url_shortener.url.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.entity.UrlEntity;

public interface UrlJpaRepository extends JpaRepository<UrlEntity, String> {
    
}
