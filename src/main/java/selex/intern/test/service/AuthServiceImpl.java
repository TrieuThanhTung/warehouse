package selex.intern.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import selex.intern.test.dto.LoginDto;
import selex.intern.test.dto.UserDto;
import selex.intern.test.exception.UserException;
import selex.intern.test.model.Role;
import selex.intern.test.model.UserEntity;
import selex.intern.test.repository.UserRepository;
import selex.intern.test.security.JwtService;
import selex.intern.test.shared.Message;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Override
    public void registerUser(UserDto user) throws UserException {
        Optional<UserEntity> existsUser = userRepository.findByEmail(user.getEmail());

        if(existsUser.isPresent()) {
            throw new UserException(Message.EMAIL_ALREADY_IN_USE);
        }

        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public String loginUser(LoginDto loginData) throws UserException {
        UserEntity existsUser = userRepository.findByEmail(loginData.getEmail())
                .orElseThrow(() -> new UserException(Message.USER_WITH_GIVEN_EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(loginData.getPassword(), existsUser.getPassword())) {
            throw new UserException(Message.PASSWORD_NOT_MATCH);
        }

        return jwtService.generateToken(existsUser.getEmail());
    }
}
