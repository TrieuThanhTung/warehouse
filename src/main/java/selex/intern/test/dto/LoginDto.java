package selex.intern.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    private String email;
    @NotBlank
    private String password;
}
