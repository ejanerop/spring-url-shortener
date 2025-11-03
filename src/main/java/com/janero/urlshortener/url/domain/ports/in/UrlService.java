package com.janero.urlshortener.url.domain.ports.in;

import com.janero.urlshortener.shared.domain.exception.EncodeException;
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
import com.janero.urlshortener.url.domain.model.Url;

public interface UrlService {

    Url shorten(String url) throws EncodeException;

    Url findByKey(String key) throws KeyNotFoundException;

    String findUrlByKey(String key) throws KeyNotFoundException;

    void deleteByKey(String key) throws KeyNotFoundException;

}
