package fa.edu.ktxdmc_be.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fa.edu.ktxdmc_be.dto.request.ChangePassWordRequest;
import fa.edu.ktxdmc_be.dto.request.RegisterRequest;
import fa.edu.ktxdmc_be.model.User;

/**
 * Represents an CustomUserDetailsService
 * 
 * @author
 */
public interface CustomUserDetailsService extends UserDetailsService {
	/**
	 * Load user details by their phone number.
	 *
	 * @param phoneNumber The phone number of the user to load.
	 * @return A UserDetails object representing the user's details.
	 * @throws UsernameNotFoundException if the user with the specified phone number
	 *                                   is not found.
	 */
	@Override
	UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException;

	/**
	 * Get a user by their user ID.
	 *
	 * @param userId The unique identifier of the user to retrieve.
	 * @return The user object associated with the specified user ID.
	 * @throws UsernameNotFoundException if the user with the specified user ID is
	 *                                   not found.
	 */
	User getUserByUserId(long userId) throws UsernameNotFoundException;

	/**
	 * Get the user ID by their phone number.
	 *
	 * @param phoneNumber The phone number of the user for which to fetch the user
	 *                    ID.
	 * @return The user ID associated with the specified phone number.
	 * @throws UsernameNotFoundException if the user with the specified phone number
	 *                                   is not found.
	 */
	long getUserIdByPhoneNumber(String phoneNumber) throws UsernameNotFoundException;

	/**
	 * Get the user ID by their CMND (Citizen Identification) number.
	 *
	 * @param cmnd The CMND number of the user for which to fetch the user ID.
	 * @return The user ID associated with the specified CMND number.
	 * @throws UsernameNotFoundException if the user with the specified CMND number
	 *                                   is not found.
	 */
	long getUserIdByCmnd(String cmnd) throws UsernameNotFoundException;

	/**
	 * Get a user by their phone number.
	 *
	 * @param phoneNumber The phone number of the user to retrieve.
	 * @return The user object associated with the specified phone number.
	 * @throws UsernameNotFoundException if the user with the specified phone number
	 *                                   is not found.
	 */
	User getUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException;

	/**
	 * Save a new user based on the provided registration request.
	 *
	 * @param registerRequest The registration request containing user information.
	 * @return An integer representing the result of the user save operation (0 if
	 *         failed, 1 if successful).
	 */
	int saveUser(RegisterRequest registerRequest);

	/**
	 * Initialize the password for a user by their user ID.
	 *
	 * @param userID The unique identifier of the user for which to initialize the
	 *               password.
	 */
	String initPasswordUser(long userID);

	/**
	 * Changes the password of a user based on the provided change password request.
	 *
	 * @param changePassWordRequest The change password request containing the
	 *                              necessary information for the change.
	 * @return true if the password change is successful, false otherwise if the
	 *         change cannot be performed.
	 */
	boolean changePassword(ChangePassWordRequest changePassWordRequest);
}
