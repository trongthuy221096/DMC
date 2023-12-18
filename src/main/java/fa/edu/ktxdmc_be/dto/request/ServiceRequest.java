package fa.edu.ktxdmc_be.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class ServiceRequest {
	@NotNull
	private Long serviceID;
	
	@NotNull
	@Size(max = 50)
	private String serviceName;
	
	@NotNull
	private Double price;
	
	@NotNull
	@Size(max = 50)
	private String unit;

}
