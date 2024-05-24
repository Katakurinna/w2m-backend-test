package me.cerrato.w2m.spaceships;

import me.cerrato.w2m.spaceships.domain.utils.ApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigTest {

    @Autowired
    private ApplicationConfig config;

    @Test
    public void checkConfigValueIsNotNull() {
        Assertions.assertNotNull(config);
        Assertions.assertNotNull(config.getDatabasePageSize());
    }

}
