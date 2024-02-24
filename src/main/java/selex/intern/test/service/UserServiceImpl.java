package selex.intern.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import selex.intern.test.dto.ChangeProfileDto;
import selex.intern.test.exception.UserException;
import selex.intern.test.model.UserEntity;
import selex.intern.test.repository.UserRepository;
import selex.intern.test.shared.Message;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity changeProfile(ChangeProfileDto changeProfileDto) throws UserException {
        UserEntity existsUser = getCurrentUser();

        if(!existsUser.getEmail().equalsIgnoreCase(changeProfileDto.getEmail())) {
            Optional<UserEntity> existsUserWithName = userRepository.findByEmail(changeProfileDto.getEmail());

            if(existsUserWithName.isPresent()) {
                throw new UserException(Message.EMAIL_ALREADY_IN_USE);
            }
        }

        existsUser.setName(changeProfileDto.getName());
        existsUser.setEmail(changeProfileDto.getEmail());

        return userRepository.save(existsUser);
    }

    @Override
    public void deleteUser(Integer id) throws UserException {
        UserEntity user =  userRepository.findById(id).
                orElseThrow(() -> new UserException(Message.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        userRepository.delete(user);
    }


}
