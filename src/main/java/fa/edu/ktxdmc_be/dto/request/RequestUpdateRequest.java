package fa.edu.ktxdmc_be.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an RequestUpdateDTO
 *
 * @author ThuyTT77 1996-10-22
 */

@Getter
@Setter
@AllArgsConstructor
public class RequestUpdateRequest {

	private String requestStatus;
	
	@NotNull
	private Long id;
}
