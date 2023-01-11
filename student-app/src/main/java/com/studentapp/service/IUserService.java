package com.studentapp.service;

import com.studentapp.dto.AddUserRequest;
import com.studentapp.jwt.JwtUser;

public interface IUserService {

    JwtUser loadUserByUsername(String username);
    Boolean addUser(AddUserRequest request);
}
