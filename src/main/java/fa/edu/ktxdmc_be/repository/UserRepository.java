package fa.edu.ktxdmc_be.repository;

import fa.edu.ktxdmc_be.dto.response.UserResponse;
import fa.edu.ktxdmc_be.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Represents an UserRepository
 *
 * @author
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their unique user ID.
     *
     * @param userId The unique identifier of the user to retrieve.
     * @return The user object associated with the specified user ID.
     */
    User findUserByUserID(long userId);

    /**
     * Find a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to retrieve.
     * @return The user object associated with the specified phone number.
     */
    User findUserByPhoneNumber(String phoneNumber);

    /**
     * Find users of a room by the room's ID using a native SQL query.
     *
     * @param id The unique identifier of the room for which to fetch users.
     * @return A list of user objects associated with the specified room ID.
     */
    @Query(value = "select * from user_member where roomid = :id", nativeQuery = true)
    List<User> findUsersOfRoom(@Param("id") Long id);


    /**
     * Find a user by their CMND (Citizen Identification) number.
     *
     * @param cmnd The CMND number of the user to retrieve.
     * @return The user object associated with the specified CMND number.
     */
    User findUserByCmnd(String cmnd);


    /**
     * Retrieve a paginated list of all users with additional room information using
     * a custom JPQL query.
     *
     * @param pageable The pageable object for pagination.
     * @return A paginated list of UserResponse objects containing user and room
     * details.
     */
    @Query(value = "select new fa.edu.ktxdmc_be.dto.response.UserResponse(u.userID, u.name, u.gender, u.birthDay,\n"
            + "u.address, u.email, u.cmnd, u.phoneNumber, r.roomName)\n" + "from User u left join Room r\n"
            + "on u.room = r where u.role = 'USER'")
    Page<UserResponse> findAllUser(Pageable pageable);

    /**
     * Retrieve a paginated list of users by CMND (Citizen Identification) number
     * using a custom JPQL query.
     *
     * @param valueSearch The CMND number to search for.
     * @param pageable    The pageable object for pagination.
     * @return A paginated list of UserResponse objects containing user and room
     * details matching the CMND number.
     */
    @Query(value = "select new fa.edu.ktxdmc_be.dto.response.UserResponse(u.userID, u.name, u.gender, u.birthDay,\n"
            + "u.address, u.email, u.cmnd, u.phoneNumber, r.roomName)\n" + "from User u left join Room r\n"
            + "on u.room = r\n" + "where u.cmnd like CONCAT('%', :valueSearch, '%') AND u.role = 'USER'")
    Page<UserResponse> findAllByCmnd(@Param("valueSearch") String valueSearch, Pageable pageable);

    /**
     * Find a user by their unique user ID and return it as an Optional
     * UserResponse.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return An Optional containing the UserResponse object associated with the
     * specified user ID.
     */
    @Query(value = "select new fa.edu.ktxdmc_be.dto.response.UserResponse(u.userID, u.name, u.gender, u.birthDay,\n"
            + "u.address, u.email, u.cmnd, u.phoneNumber, r.roomName)\n" + "from User u left join Room r\n"
            + "on u.room = r\n" + "where u.userID = :id ")
    Optional<UserResponse> findUserById(@Param("id") Long id);

    /**
     * Find users by their isActive status.
     *
     * @param isActive The isActive status to search for (true or false).
     * @return A list of user objects matching the specified isActive status.
     */
    List<User> findByIsActiveIs(boolean isActive);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to be retrieved.
     * @return The User object matching the provided email address, or null if not
     * found.
     */
    User findByEmail(String email);

    /**
     * Finds a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to be retrieved.
     * @return The User object matching the provided phone number, or null if not
     * found.
     */
    User findByPhoneNumber(String phoneNumber);
}
