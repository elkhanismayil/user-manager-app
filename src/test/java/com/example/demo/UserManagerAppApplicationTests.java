package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest(classes = {AnnotationConfigContextLoader.class})
class UserManagerAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
