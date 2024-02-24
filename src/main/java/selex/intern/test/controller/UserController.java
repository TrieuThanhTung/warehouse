package selex.intern.test.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import selex.intern.test.dto.ChangeProfileDto;
import selex.intern.test.dto.UserDto;
import selex.intern.test.exception.UserException;
import selex.intern.test.model.UserEntity;
import selex.intern.test.service.UserService;
import selex.intern.test.shared.GenericResponse;
import selex.intern.test.shared.Message;
import selex.intern.test.shared.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GenericResponse>  getAllUsersHandler() {
        List<UserEntity> userList = userService.getAllUsers();

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, userList));
    }

    @GetMapping("/profile")
    public ResponseEntity<GenericResponse>  getProfileUsersHandler() throws UserException {
        UserEntity user = userService.getCurrentUser();

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, user));
    }

    @PutMapping
    public ResponseEntity<GenericResponse> changeProfileHandler(@Valid @RequestBody ChangeProfileDto changeProfileDto)
            throws UserException {
        UserEntity user = userService.changeProfile(changeProfileDto);

        return ResponseEntity.ok(new GenericResponse(Message.CHANGE_SUCCESSFULLY, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUserHandler(@PathVariable("id") Integer id) throws UserException {
        userService.deleteUser(id);

        return ResponseEntity.ok(new MessageResponse(Message.DELETE_SUCCESSFULLY));
    }
}
