package selex.intern.test.service;

import selex.intern.test.dto.ChangeProfileDto;
import selex.intern.test.exception.UserException;
import selex.intern.test.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getCurrentUser() throws UserException;

    List<UserEntity> getAllUsers();

    UserEntity changeProfile(ChangeProfileDto changeProfileDto) throws UserException;

    void deleteUser(Integer id) throws UserException;
}
