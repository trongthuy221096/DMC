package fa.edu.ktxdmc_be.controller;

import fa.edu.ktxdmc_be.dto.request.ServiceRequest;
import fa.edu.ktxdmc_be.dto.response.ServiceResponse;
import fa.edu.ktxdmc_be.model.Service;
import fa.edu.ktxdmc_be.service.ServiceService;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Represents an RequestController
 *
 * @author ThuyTT77
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("service")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;

	/**
	 * Retrieve a paginated list of services or perform searches based on various
	 * criteria.
	 *
	 * @param page        The page number for the result (default is 1 if not
	 *                    provided).
	 * @param pageSize    The number of services per page in the result (default is
	 *                    5 if not provided).
	 * @param valueSearch The value to search for services (default is an empty
	 *                    string if not provided).
	 * @return ResponseEntity containing a paginated list of ServiceResponse objects
	 *         if successful, or a "NO_CONTENT" response status if there are no
	 *         services, or an "INTERNAL_SERVER_ERROR" response status if an error
	 *         occurs during processing.
	 * @author ThuyTT77
	 */
	@GetMapping("/list")
	public ResponseEntity<?> getAllService(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(name = "valueSearch", defaultValue = "") String valueSearch) {
		valueSearch = valueSearch.trim();
		try {
			PageRequest pageableService = PageRequest.of(page - 1, pageSize);
			Page<Service> servicePage = null;

			if ("".equals(valueSearch)) {
				servicePage = serviceService.findAll(pageableService);
			} else {
				Double searchPrice = null;
				try {
					searchPrice = Double.parseDouble(valueSearch);
					servicePage = serviceService.findByPrice(searchPrice, pageableService);
				} catch (NumberFormatException e) {
					servicePage = serviceService.findByServiceName(valueSearch, pageableService);
				}

			}

			if (servicePage == null || servicePage.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			Page<ServiceResponse> serviceResponse = servicePage.map(ServiceResponse::new);

			return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * PUT endpoint to update service information.
	 *
	 * @param serviceRequest The {@link ServiceRequest} object containing the updated service information.
	 * @param result         The {@link BindingResult} object for validating errors.
	 * @return A {@link ResponseEntity} containing the result of the service information update operation.
	 *         - If there are no validation errors and the update is successful, it returns OK with a success message.
	 *         - If the service to be updated is not found, it returns NOT_FOUND with a corresponding message.
	 *         - If there are validation errors, it returns BAD_REQUEST.
	 *         - If an exception occurs during the update process, it returns INTERNAL_SERVER_ERROR.
	 */
	@PutMapping("/update")
	public ResponseEntity<?> updateService(@Valid @RequestBody ServiceRequest serviceRequest, BindingResult result) {
		try {
			if (!result.hasErrors()) {
				Service service = new Service();
				BeanUtils.copyProperties(serviceRequest, service);
				boolean success = serviceService.updateService(service);
				if (success) {
					return ResponseEntity.ok("Update thành công.");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy yêu cầu.");
				}
			}
			{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
