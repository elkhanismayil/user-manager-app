package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.util.UserPageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser_Test() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("elxan310@gmail.com");

        when(userService.create(userDTO)).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("elxan310@gmail.com")));

        verify(userService, times(1)).create(userDTO);
    }

    @Test
    void getAll_Test() throws Exception{
        UserPageResponse pageResponse = new UserPageResponse();

        when(userService.getAllUser(pageResponse.getPageNo(), pageResponse.getPageSize())).thenReturn(pageResponse);

        mockMvc.perform(get("/api/users/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(pageResponse)))
                .andExpect(status().isOk());
    }

    @Test
    void findUserById_Test() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        long id = 1;

        when(userService.getUserById(userDTO.getId())).thenReturn(userDTO);
        mockMvc.perform(get("/api/users/one/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    void findUserByName_Test() throws Exception{
        UserDTO userDTO = new UserDTO();
        String name = "Elkhan";

        when(userService.getUserById(userDTO.getId())).thenReturn(userDTO);
        mockMvc.perform(get("/api/users/{name}", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userDTO)))
                .andExpect(status().isOk());

    }

}