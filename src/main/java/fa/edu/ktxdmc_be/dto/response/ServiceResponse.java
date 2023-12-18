package fa.edu.ktxdmc_be.dto.response;

import org.springframework.beans.BeanUtils;

import fa.edu.ktxdmc_be.model.Service;
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
public class ServiceResponse {

	private Long serviceID;

	private String serviceName;

	private Double price;

	private String unit;

	public ServiceResponse(Service service) {
		if (service != null) {
			BeanUtils.copyProperties(service, this);

		}
	}

}
