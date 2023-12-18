package fa.edu.ktxdmc_be.dto.response;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import fa.edu.ktxdmc_be.model.Request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an RequestDTO
 * 
 * @author ThuyTT77 1996-10-22
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestResponse {

	private Long requestID;

	private String requestStatus;

	private String requestContent;

	private LocalDate DateOfCreate;

	private LocalDate dateOfUpdate;

	private String roomName;

	private String userName;

	private String area;
	
	private String role;

	public RequestResponse(Request request) {
		if (request != null) {
			BeanUtils.copyProperties(request, this);
			this.roomName = request.getUser().getRoom().getRoomName();
			this.userName = request.getUser().getName();
			this.area = request.getUser().getRoom().getArea();
			this.role = request.getUser().getRole();
		}
	}

}
