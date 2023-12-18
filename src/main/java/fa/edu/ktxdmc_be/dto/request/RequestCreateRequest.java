package fa.edu.ktxdmc_be.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreateRequest {
	@NotNull
    private long userID;
    
	@NotNull
    private String requestContent;
	
    private String requestStatus;
}
