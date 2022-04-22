package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UserPageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AmqpTemplate rabbitTemplate;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository, userMapper, rabbitTemplate);
    }

    @Test
    void getAllUser_Test() {
        ArrayList<User> userList = new ArrayList<>();
        when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<>(userList));
        when(this.userMapper.toDtoList(any())).thenReturn(new ArrayList<>());

        UserPageResponse actualAllUser = this.userServiceImpl.getAllUser(1, 3);

        assertEquals(userList, actualAllUser.getContent());

        assertTrue(actualAllUser.isLast());
        assertEquals(1, actualAllUser.getTotalPages());
        assertEquals(0L, actualAllUser.getTotalElements());
        assertEquals(0, actualAllUser.getPageSize());
        assertEquals(0, actualAllUser.getPageNo());
        verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.userMapper).toDtoList(any());
    }


    @Test
    void getUserById_Test() {
        User user = new User();
        user.setDob(new Date(1988, Calendar.JUNE, 17));
        user.setEmail("elxan310@gmail.com");
        user.setId(1L);
        user.setJobDescription("PC Programmer");
        user.setName("Elkhan");
        user.setNationality("az");
        user.setPhone("+994705023590");
        user.setSurname("Ismayilov");
        user.setUserCode("User Code");

        when(this.userRepository.findById(anyLong())).thenReturn(user);

        UserDTO userDTO = new UserDTO();

        when(this.userMapper.userToUserDto(any())).thenReturn(userDTO);
        assertSame(userDTO, this.userServiceImpl.getUserById(1L));
        verify(this.userRepository).findById(anyLong());
        verify(this.userMapper).userToUserDto(any());
    }

    @Test
    void getUserByName_Test() {
        User user = new User();
        user.setDob(new Date(1988, Calendar.JUNE, 17));
        user.setEmail("elxan310@gmail.com");
        user.setId(1L);
        user.setJobDescription("PC Programmer");
        user.setName("Elkhan");
        user.setNationality("az");
        user.setPhone("+994705023590");
        user.setSurname("Ismayilov");
        user.setUserCode("User Code");

        when(this.userRepository.findByName(anyString())).thenReturn(user);

        UserDTO userDTO = new UserDTO();

        when(this.userMapper.userToUserDto(any())).thenReturn(userDTO);
        assertSame(userDTO, this.userServiceImpl.getUserByName("Elkhan"));
        verify(this.userRepository).findByName(anyString());
        verify(this.userMapper).userToUserDto(any());
    }
}