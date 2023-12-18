package fa.edu.ktxdmc_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	@Size(min = 5, max = 50)
	private String name;

	@Pattern(regexp = "^(Nam|Nữ|Khác)$", message = "Chỉ chấp nhận 'Nam', 'Nữ', hoặc 'Khác'")
	private String gender;

	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
	private LocalDate birthDay;

	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
	private String email;

	@Pattern(regexp = "^[0-9]{10,12}$")
	private String cmnd;

	@Pattern(regexp = "(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b")
	private String phoneNumber;

	@NotBlank
	private String city;

	@NotBlank
	private String district;
	
	@NotBlank
	private String ward;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private long roomID;
}
