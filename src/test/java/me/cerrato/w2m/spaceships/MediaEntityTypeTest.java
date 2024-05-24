package me.cerrato.w2m.spaceships;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MediaEntityTypeTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void whenMediaTypeExist_thenMediaIsCreated() {
        Query nativeQuery = entityManager.createNativeQuery("INSERT INTO MEDIA (id, name, type) VALUES (0, 'okTest', 'SERIES')");
        nativeQuery.executeUpdate();
    }

    @Test
    @Transactional
    public void whenMediaTypeDontExist_thenMediaIsNotCreated() {
        Query nativeQuery = entityManager.createNativeQuery("INSERT INTO MEDIA (id, name, type) VALUES (0, 'errorTest', 'NOT_EXIST')");
        Assertions.assertThrows(org.hibernate.exception.DataException.class, () -> nativeQuery.executeUpdate());
    }

}
