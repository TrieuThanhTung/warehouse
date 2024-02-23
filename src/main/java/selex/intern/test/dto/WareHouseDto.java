package selex.intern.test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WareHouseDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
