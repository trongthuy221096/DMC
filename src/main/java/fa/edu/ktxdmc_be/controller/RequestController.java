package fa.edu.ktxdmc_be.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.ktxdmc_be.common.Ultis;
import fa.edu.ktxdmc_be.dto.request.RequestCreateRequest;
import fa.edu.ktxdmc_be.dto.request.RequestUpdateRequest;
import fa.edu.ktxdmc_be.dto.response.MessageResponse;
import fa.edu.ktxdmc_be.dto.response.RequestResponse;
import fa.edu.ktxdmc_be.model.Request;
import fa.edu.ktxdmc_be.service.RequestService;

/**
 * Represents an RequestController
 *
 * @author ThuyTT77
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("request")
public class RequestController {

	@Autowired
	private RequestService requestService;

	/**
	 * Retrieve a paginated list of requests or perform searches based on various
	 * criteria.
	 *
	 * @param page        The page number for the result (default is 1 if not
	 *                    provided).
	 * @param pageSize    The number of requests per page in the result (default is
	 *                    5 if not provided).
	 * @param valueSearch The value to search for requests (default is an empty
	 *                    string if not provided).
	 * @param tuNgay      The start date for filtering requests (default is an empty
	 *                    string if not provided).
	 * @param denNgay     The end date for filtering requests (default is an empty
	 *                    string if not provided).
	 * @return ResponseEntity containing a paginated list of RequestResponse objects
	 *         if successful, or a "NO_CONTENT" response status if there are no
	 *         requests, or an "INTERNAL_SERVER_ERROR" response status if an error
	 *         occurs during processing.
	 * @author ThuyTT77
	 */
	@GetMapping("/list")
	public ResponseEntity<?> getAllRequests(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(name = "valueSearch", defaultValue = "") String valueSearch,
			@RequestParam(name = "tuNgay", defaultValue = "") String tuNgay,
			@RequestParam(name = "denNgay", defaultValue = "") String denNgay) {
		valueSearch = valueSearch.trim();
		try {
			PageRequest pageableRequest = PageRequest.of(page - 1, pageSize);
			Page<Request> requestPage = null;
			LocalDate tuNgayDate = Ultis.stringToLocalDate(tuNgay);
			LocalDate denNgayDate = Ultis.stringToLocalDate(denNgay);

			if (tuNgayDate != null && denNgayDate != null) {
				requestPage = requestService.findByDateOfCreateAndUserOrRoomOrRequestStatus(tuNgayDate, denNgayDate,
						valueSearch, pageableRequest);
			} else if (denNgayDate != null) {
				requestPage = requestService.findByDateOfCreateLessThan(denNgayDate, valueSearch, pageableRequest);
			} else if (tuNgayDate != null) {
				requestPage = requestService.findByDateOfCreateGreaterThan(tuNgayDate, valueSearch, pageableRequest);
			} else if ("".equals(valueSearch)) {
				requestPage = requestService.findAll(pageableRequest);
			} else {
				requestPage = requestService.findByUserOrRoomOrRequestStatus(valueSearch, pageableRequest);
			}

			if (requestPage == null || requestPage.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			// Chuyển đổi từ Page<Request> sang Page<RequestDTO>
			Page<RequestResponse> requestDtoPage = requestPage.map(RequestResponse::new);

			return new ResponseEntity<>(requestDtoPage, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * POST endpoint to create a new request.
	 *
	 * @param request The {@link RequestCreateRequest} object containing information
	 *                for the new request.
	 * @param result  The {@link BindingResult} object for validating errors.
	 * @return A {@link ResponseEntity} containing the result of the request
	 *         creation operation. It can be a BAD_REQUEST if there are validation
	 *         errors, or OK if the request is successfully created.
	 */
	@PostMapping("/create")
	public ResponseEntity<?> createRequest(@Valid @RequestBody RequestCreateRequest request, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			requestService.saveRequest(request);
		}

		return ResponseEntity.ok().body(new MessageResponse("Gửi yêu cầu thành công"));
	}

	/**
	 * Updates the status of a request based on the provided update request.
	 *
	 *
	 * @param requestUpdateRequest The update request containing the ID of the
	 *                             request and the new status to be set.
	 * @param result               The result of the validation check for the update
	 *                             request.
	 *
	 * @return An HTTP response indicating the result of the update operation. - If
	 *         the update is successful, an HTTP 200 OK response with a success
	 *         message. - If the request is not found, an HTTP 404 Not Found
	 *         response with an error message. - If the request is already in a
	 *         "refused" or "accepted" state, an HTTP 202 Accepted response
	 *         indicating that the request cannot be updated further. - If the
	 *         update request is invalid or contains errors, an HTTP 400 Bad Request
	 *         response. - If an internal server error occurs during the update, an
	 *         HTTP 500 Internal Server Error response.
	 */

	@PutMapping("/update")
	public ResponseEntity<?> updateRequest(@Valid @RequestBody RequestUpdateRequest requestUpdateRequest,
			BindingResult result) {
		try {

			if (!result.hasErrors()) {
				Request request = requestService.findRequestByID(requestUpdateRequest.getId());
				if (request != null) {
					if (request.getRequestStatus().equals("refused") || request.getRequestStatus().equals("accepted")) {
						return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
					}
				}
				boolean success = requestService.updateRequestStatus(requestUpdateRequest);

				if (success) {
					return ResponseEntity.ok("Update Thành công.");
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

	/**
	 * Delete a request by its unique request ID.
	 *
	 * @param requestID The unique identifier of the request to be deleted.
	 * @return ResponseEntity with a success message "Xóa thành công." if the
	 *         deletion is successful, or a "Không tìm thấy request." message with a
	 *         "NOT_FOUND" status if the request is not found.
	 * @author ThuyTT77
	 */
	@DeleteMapping("/delete/{requestID}")
	public ResponseEntity<?> deleteRequest(@PathVariable Long requestID) {
		boolean success = requestService.deleteRequest(requestID);
		if (success) {
			return ResponseEntity.ok("Xóa thành công.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy request.");
		}
	}
}
