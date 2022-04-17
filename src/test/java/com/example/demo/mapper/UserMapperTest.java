package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void givenUserToUserDto_whenMaps_thenCorrect(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Tom");
        userDTO.setSurname("Cruise");
        userDTO.setEmail("tomcruise@gmail.com");
        userDTO.setNationality("american");
        userDTO.setJobDescription("Actor");

        User user = userMapper.userDtoToUser(userDTO);

        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getNationality(), user.getNationality());
        assertEquals(userDTO.getJobDescription(), user.getJobDescription());

    }

    @Test
    public void givenUserDtoToUser_whenMaps_thenCorrect(){
        User user = new User();
        user.setName("Tom");
        user.setSurname("Cruise");
        user.setEmail("tomcruise@gmail.com");
        user.setNationality("american");
        user.setJobDescription("Actor");

        UserDTO userDTO = userMapper.userToUserDto(user);

        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getSurname(), user.getSurname());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getNationality(), user.getNationality());
        assertEquals(userDTO.getJobDescription(), user.getJobDescription());

    }

}