package selex.intern.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import selex.intern.test.exception.UserException;
import selex.intern.test.model.UserEntity;
import selex.intern.test.repository.UserRepository;
import selex.intern.test.shared.Message;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity getCurrentUser() throws UserException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(userEmail).
                orElseThrow(() -> new UserException(Message.USER_WITH_GIVEN_EMAIL_NOT_FOUND));
    }
}
