package selex.intern.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selex.intern.test.dto.LoginDto;
import selex.intern.test.dto.UserDto;
import selex.intern.test.exception.UserException;
import selex.intern.test.service.AuthService;
import selex.intern.test.shared.GenericResponse;
import selex.intern.test.shared.Message;
import selex.intern.test.shared.MessageResponse;
import selex.intern.test.shared.response.TokenResponse;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Register user")
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerHandler(@Valid @RequestBody UserDto user) throws UserException {
         authService.registerUser(user);

        return new ResponseEntity<>(new MessageResponse(Message.REGISTER_SUCCESSFULLY), HttpStatus.CREATED);
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> loginHandler(@Valid @RequestBody LoginDto loginData) throws UserException {
        String token = authService.loginUser(loginData);

        GenericResponse response = new GenericResponse(
                Message.LOGIN_SUCCESSFULLY,
                new TokenResponse(token)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
