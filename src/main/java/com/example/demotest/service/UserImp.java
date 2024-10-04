package com.example.demotest.service;

import com.example.demotest.config.AuthConfig;
import com.example.demotest.dto.request.UserRequestDto;
import com.example.demotest.dto.response.UserResponseDto;
import com.example.demotest.entity.UserEnitiy;
import com.example.demotest.exp.UserAlreadyExistsException;
import com.example.demotest.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserImp implements UserService {

    @Autowired
    private UserRepo userRepo;
  
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private AuthConfig authConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // UserEnitiy user = userRepo.findByEmail(username).orElseThrow(()->new RuntimeException("User not found"));
        Optional<UserEnitiy> userOp = userRepo.findByEmail(username);
        if (!userOp.isPresent())
            throw new UsernameNotFoundException("User not found");

        UserEnitiy user = userOp.get();
        log.info("Retrived Data");
        log.info(user.getPassword()+"Retrived Password");
        log.info(user.getUsername());
        log.info("user id {} ", user.getId());
        log.info(user.getEmail());
        log.info("-----");
        return user;
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        try {
            List<UserEnitiy> userEnitiys = userRepo.findAll();
            List<UserResponseDto> userResponseDtoList = userEnitiys.stream().map(user -> this.userEntityToUserRespDto(user)).collect(Collectors.toList());
            return userResponseDtoList;
        }catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UsernameNotFoundException(ex.getMessage());
        }



    }
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<UserEnitiy> foundUser = this.userRepo.findByEmail(userRequestDto.getEmail());
        if (foundUser.isEmpty()) {
            UserEnitiy user = this.userReqDtoToUserEntity(userRequestDto);
            user.setPassword(authConfig.passwordEncoder().encode(user.getPassword()));
            UserEnitiy createdUser = userRepo.save(user);
            return this.userEntityToUserRespDto(createdUser);
        } else {
            // User already exists, throw an exception
            throw new UserAlreadyExistsException("User with email " + userRequestDto.getEmail() + " already exists");
        }
    }


    public UserEnitiy userReqDtoToUserEntity(UserRequestDto userReqDto){
        UserEnitiy user = this.dozerMapper.map(userReqDto,UserEnitiy.class);
        return user;
    }
    public UserResponseDto userEntityToUserRespDto(UserEnitiy user){
        UserResponseDto userRespDto = this.dozerMapper.map(user,UserResponseDto.class);
        return userRespDto;
    }
}

