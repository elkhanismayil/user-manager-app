package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("Elxan");
        user.setSurname("Ismayilov");
        user.setEmail("elxan310@gmail.com");
        user.setPhone("+994705023590");
        user.setNationality("Azerbaijan");
        user.setJobDescription("Job Desc");
        user.setDob(new Date(1988, Calendar.JUNE, 17));
        user.setUserCode(UUID.randomUUID().toString());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void whenSaved_thenFindById() {
        repository.save(User.builder().id(1L).build());
        assertThat(repository.findById(1L)).isNotNull();
    }


    @Test
    void whenSaved_thenFindByName() {
        repository.save(User.builder().name("Elxan").build());
        assertThat(repository.findByName("Elxan")).isNotNull();
    }

}