package fa.edu.ktxdmc_be.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import fa.edu.ktxdmc_be.dto.request.RequestCreateRequest;
import fa.edu.ktxdmc_be.dto.request.RequestUpdateRequest;
import fa.edu.ktxdmc_be.model.Request;

/**
 * Represents an RequestService
 *
 * @author ThuyTT77
 */
public interface RequestService {

	/**
	 * Finds requests by searching for a given text within user names, room names,
	 * or request statuses.
	 *
	 * @param searchValue The text to search for within user names, room names, or
	 *                    request statuses.
	 * @param pageRequest Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the search criteria.
	 */
	Page<Request> findByUserOrRoomOrRequestStatus(String searchValue, PageRequest pageRequest);

	/**
	 * Retrieves all requests in the system.
	 *
	 * @param pageRequest Pagination and sorting options for the query results.
	 * @return A Page of all Request objects in the system.
	 */
	Page<Request> findAll(PageRequest pageRequest);

	/**
	 * Updates the status of a request based on the provided request update request.
	 *
	 * @param requestUpdateRequest The request containing the updated status
	 *                             information.
	 * @return True if the request status is successfully updated, false otherwise.
	 */
	boolean updateRequestStatus(RequestUpdateRequest requestUpdateRequest);

	/**
	 * Finds requests created between two given dates and matching a given search
	 * text within user names, room names, or request statuses.
	 *
	 * @param tuNgay      The start date for the search range.
	 * @param denNgay     The end date for the search range.
	 * @param searchText  The text to search for within user names, room names, or
	 *                    request statuses.
	 * @param pageRequest Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the date range and search
	 *         criteria.
	 */
	Page<Request> findByDateOfCreateAndUserOrRoomOrRequestStatus(LocalDate tuNgay, LocalDate denNgay, String searchText,
			PageRequest pageRequest);

	/**
	 * Finds requests created on or before a given date and matching a given search
	 * text within user names, room names, or request statuses.
	 *
	 * @param denNgay     The end date for the search range.
	 * @param searchText  The text to search for within user names, room names, or
	 *                    request statuses.
	 * @param pageRequest Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the date range and search
	 *         criteria.
	 */
	Page<Request> findByDateOfCreateLessThan(LocalDate denNgay, String searchText, PageRequest pageRequest);

	/**
	 * Finds requests created on or after a given date and matching a given search
	 * text within user names, room names, or request statuses.
	 *
	 * @param tuNgay      The start date for the search range.
	 * @param searchText  The text to search for within user names, room names, or
	 *                    request statuses.
	 * @param pageRequest Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the date range and search
	 *         criteria.
	 */
	Page<Request> findByDateOfCreateGreaterThan(LocalDate tuNgay, String searchText, PageRequest pageRequest);

	/**
	 * Deletes a request based on its unique request ID.
	 *
	 * @param requestID The ID of the request to be deleted.
	 * @return True if the request is successfully deleted, false otherwise.
	 */
	boolean deleteRequest(Long requestID);

	/**
	 * Saves a new request based on the provided request creation request.
	 *
	 * @param request The request object containing the details of the request to be
	 *                created.
	 */
	void saveRequest(RequestCreateRequest request);

	/**
	 * Retrieves the user ID associated with a request based on its unique request
	 * ID.
	 *
	 * @param requestID The ID of the request for which to retrieve the user ID.
	 * @return The user ID associated with the specified request.
	 */
	long getUserIDByRequestID(long requestID);
	
	/**
	 * Finds and retrieves a request by its unique ID.
	 *
	 * @param requestID The unique ID of the request to be retrieved.
	 * @return The request object with the specified ID, or null if no matching request is found.
	 */
	Request findRequestByID(long requestID);

}
