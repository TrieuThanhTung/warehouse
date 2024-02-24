package selex.intern.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeProfileDto {
    @NotBlank
    private String name;
    @Email
    private String email;
}
