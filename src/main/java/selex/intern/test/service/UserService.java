package selex.intern.test.service;

import selex.intern.test.exception.UserException;
import selex.intern.test.model.UserEntity;

public interface UserService {
    UserEntity getCurrentUser() throws UserException;
}
