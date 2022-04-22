package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.UserPageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    public final UserMapper userMapper;

    public final AmqpTemplate rabbitTemplate;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AmqpTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${user.manager.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${user.manager.rabbitmq.routingKey}")
    private String routingKeyName;


    @Override
    public UserDTO create(UserDTO userDTO) {
        userDTO.setUserCode(UUID.randomUUID().toString());
        User user = userMapper.userDtoToUser(userDTO);
        User savedUser = userRepository.save(user);
        sendToQueue(userDTO);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserPageResponse getAllUser(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> users = userPage.getContent();
        List<UserDTO> content = userMapper.toDtoList(users);
        UserPageResponse response = new UserPageResponse();
        response.setContent(content);
        response.setPageNo(userPage.getNumber());
        response.setPageSize(userPage.getSize());
        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setLast(userPage.isLast());
        return response;
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDTO getUserByName(String name) {
        User user = userRepository.findByName(name);
        return userMapper.userToUserDto(user);
    }

    public void sendToQueue(UserDTO userDTO){
        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, userDTO);
        System.out.println("Message sent : " + userDTO);
    }
}
