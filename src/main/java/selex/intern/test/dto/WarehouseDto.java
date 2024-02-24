package selex.intern.test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarehouseDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
