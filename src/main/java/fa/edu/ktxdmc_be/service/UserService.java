package fa.edu.ktxdmc_be.service;

import fa.edu.ktxdmc_be.dto.response.UserResponse;
import fa.edu.ktxdmc_be.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Represents an UserService
 * 
 * @author
 */
public interface UserService {
	/**
	 * Retrieve a paginated list of all users along with their details using the
	 * provided PageRequest.
	 *
	 * @param pageable The PageRequest object for pagination.
	 * @return A paginated list of UserResponse objects containing user details.
	 */
	Page<UserResponse> findAllUser(PageRequest pageable);

	/**
	 * Retrieve a paginated list of users by their CMND (Citizen Identification)
	 * number using the provided PageRequest.
	 *
	 * @param cmnd        The CMND number to search for in users.
	 * @param pageRequest The PageRequest object for pagination.
	 * @return A paginated list of UserResponse objects matching the search
	 *         criteria.
	 */
	Page<UserResponse> findAllByCmnd(String cmnd, PageRequest pageRequest);

	/**
	 * Retrieve user details by their unique ID.
	 *
	 * @param id The unique identifier of the user to retrieve.
	 * @return A UserResponse object containing the details of the specified user.
	 */
	UserResponse findById(Long id);

	/**
	 * Finds a user by their email address.
	 *
	 * @param email The email address of the user to be retrieved.
	 * @return The User object matching the provided email address, or null if not
	 *         found.
	 */
	User findByEmail(String email);

	/**
	 * Finds a user by their phone number.
	 *
	 * @param phoneNumber The phone number of the user to be retrieved.
	 * @return The User object matching the provided phone number, or null if not
	 *         found.
	 */
	User findByPhoneNumber(String phoneNumber);
}
