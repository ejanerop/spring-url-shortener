package com.janero.spring_url_shortener.url.infrastructure.persistence.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.entity.UrlEntity;

public interface UrlJpaRepository extends JpaRepository<UrlEntity, String> {

    @Query("SELECT u.url FROM UrlEntity u WHERE u.key = :key")
    Optional<String> findUrlByKey(String key);

}
