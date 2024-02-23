package selex.intern.test.service;

import selex.intern.test.dto.LoginDto;
import selex.intern.test.dto.UserDto;
import selex.intern.test.exception.UserException;

public interface AuthService {
     void registerUser(UserDto user) throws UserException;

     String loginUser(LoginDto loginData) throws UserException;
}
