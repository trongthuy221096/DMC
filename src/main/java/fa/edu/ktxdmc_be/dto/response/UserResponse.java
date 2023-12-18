package fa.edu.ktxdmc_be.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
	private Long userID;
	private String name;
	private String gender;
	private LocalDate birthDay;
	private String address;
	private String email;
	private String cmnd;
	private String phoneNumber;
	private String roomName;

	public UserResponse(Long userID, String name, String gender, LocalDate birthDay, String address, String email,
			String cmnd, String phoneNumber, String roomName) {
		this.userID = userID;
		this.name = name;
		this.gender = gender;
		this.birthDay = birthDay;
		this.address = address;
		this.email = email;
		this.cmnd = cmnd;
		this.phoneNumber = phoneNumber;
		this.roomName = roomName;
	}

	public UserResponse(UserResponse userResponse) {
		if (userResponse != null) {
			BeanUtils.copyProperties(userResponse, this);

		}
	}

}
