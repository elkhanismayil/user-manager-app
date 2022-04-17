package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.util.UserPageResponse;

public interface UserService {
    UserDTO create(UserDTO userDTO);

    UserPageResponse getAllUser(int pageNo, int pageSize);

    UserDTO getUserById(long id);

    UserDTO getUserByName(String name);
}
