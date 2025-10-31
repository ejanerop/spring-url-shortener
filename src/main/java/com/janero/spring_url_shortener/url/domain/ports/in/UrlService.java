package com.janero.spring_url_shortener.url.domain.ports.in;

import com.janero.spring_url_shortener.shared.domain.exception.EncodeException;
import com.janero.spring_url_shortener.shared.domain.exception.KeyNotFoundException;
import com.janero.spring_url_shortener.url.domain.model.Url;

public interface UrlService {

    Url shorten(String url) throws EncodeException;

    Url findByKey(String key);

    String findUrlByKey(String key);

    void deleteByKey(String key) throws KeyNotFoundException;

}
