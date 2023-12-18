package fa.edu.ktxdmc_be.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequest {
    @NotBlank
    private String area;

    @NotBlank
    private String roomName;

    @NotNull(message = "Number of bed is required!")
    @Min(value = 0, message = "NUmber of bed must be greater than 0!")
    private int numberOfBed;

    @NotNull(message = "Price is required!")
    @Min(value = 0, message = "Price must be greater than 0!")
    private Double price;

    private String roomStatus = "empty";

}
