package com.janero.urlshortener.url.infrastructure.persistence;import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
import com.janero.urlshortener.url.domain.model.Url;
import com.janero.urlshortener.url.infrastructure.persistence.jpa.UrlJpaRepository;
import com.janero.urlshortener.url.infrastructure.persistence.jpa.entity.UrlEntity;
import com.janero.urlshortener.url.infrastructure.persistence.jpa.mapper.UrlEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlRepositoryImplTest {

    @Mock
    private UrlJpaRepository repo;

    @Mock
    private UrlEntityMapper mapper;

    @InjectMocks
    private UrlRepositoryImpl repository;

    @Test
    void save_shouldConvertEntity_saveAndReturnModel() {
        Url model = Mockito.mock(Url.class);
        UrlEntity entity = Mockito.mock(UrlEntity.class);

        when(mapper.toEntity(model)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(model);

        Url result = repository.save(model);

        assertSame(model, result);
        verify(mapper).toEntity(model);
        verify(repo).save(entity);
        verify(mapper).toModel(entity);
    }

    @Test
    void findByKey_whenFound_shouldReturnModel() throws KeyNotFoundException {
        String key = "abc";
        Url model = Mockito.mock(Url.class);
        UrlEntity entity = Mockito.mock(UrlEntity.class);

        when(repo.findById(key)).thenReturn(Optional.of(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        Url result = repository.findByKey(key);

        assertSame(model, result);
        verify(repo).findById(key);
        verify(mapper).toModel(entity);
    }

    @Test
    void findByKey_whenNotFound_shouldThrow() {
        String key = "missing";
        when(repo.findById(key)).thenReturn(Optional.empty());

        assertThrows(KeyNotFoundException.class, () -> repository.findByKey(key));
        verify(repo).findById(key);
    }

    @Test
    void findUrlByKey_whenFound_shouldReturnUrlString() throws KeyNotFoundException {
        String key = "k1";
        String url = "http://example.com";

        when(repo.findUrlByKey(key)).thenReturn(Optional.of(url));

        String result = repository.findUrlByKey(key);

        assertEquals(url, result);
        verify(repo).findUrlByKey(key);
    }

    @Test
    void findUrlByKey_whenNotFound_shouldThrow() {
        String key = "k2";
        when(repo.findUrlByKey(key)).thenReturn(Optional.empty());

        assertThrows(KeyNotFoundException.class, () -> repository.findUrlByKey(key));
        verify(repo).findUrlByKey(key);
    }

    @Test
    void existByKey_shouldReturnRepositoryValue() {
        String key = "exists";
        when(repo.existsById(key)).thenReturn(true);

        boolean result = repository.existByKey(key);

        assertTrue(result);
        verify(repo).existsById(key);
    }

    @Test
    void deleteByKey_shouldInvokeRepositoryDelete() {
        String key = "del";
        repository.deleteByKey(key);
        verify(repo).deleteById(key);
    }

}