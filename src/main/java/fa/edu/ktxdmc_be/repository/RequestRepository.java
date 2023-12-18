package fa.edu.ktxdmc_be.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fa.edu.ktxdmc_be.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Represents an RequestRepo
 *
 * @author ThuyTT77
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
	/**
	 * Finds requests by searching for a given text within user names, room names,
	 * or request statuses. The search is case-insensitive.
	 *
	 * @param searchText The text to search for within user names, room names, or
	 *                   request statuses.
	 * @param pageable   Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the search criteria.
	 */
	@Query("SELECT r FROM Request r WHERE r.user.name LIKE %:searchText% OR r.user.room.roomName LIKE %:searchText% OR r.requestStatus LIKE %:searchText%")
	Page<Request> findByUserOrRoomOrRequestStatus(@Param("searchText") String searchText, Pageable pageable);

	/**
	 * Finds requests created between two given dates and matching a given search
	 * text within user names, room names, or request statuses. The search is
	 * case-insensitive.
	 *
	 * @param tuNgay     The start date for the search range.
	 * @param denNgay    The end date for the search range.
	 * @param searchText The text to search for within user names, room names, or
	 *                   request statuses.
	 * @param pageable   Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the date range and search
	 *         criteria.
	 */
	@Query("SELECT r FROM Request r WHERE (r.DateOfCreate BETWEEN :tuNgay AND :denNgay) AND (r.user.name LIKE %:searchText% OR r.user.room.roomName LIKE %:searchText% OR r.requestStatus LIKE %:searchText%)")
	Page<Request> findByDateOfCreateAndUserOrRoomOrRequestStatus(@Param("tuNgay") LocalDate tuNgay,
			@Param("denNgay") LocalDate denNgay, @Param("searchText") String searchText, Pageable pageable);

	/**
	 * Finds requests created on or before a given date and matching a given search
	 * text within user names, room names, or request statuses. The search is
	 * case-insensitive.
	 *
	 * @param denNgay    The end date for the search range.
	 * @param searchText The text to search for within user names, room names, or
	 *                   request statuses.
	 * @param pageable   Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the date range and search
	 *         criteria.
	 */
	@Query("SELECT r FROM Request r WHERE r.DateOfCreate <= :denNgay AND (r.user.name LIKE %:searchText% OR r.user.room.roomName LIKE %:searchText% OR r.requestStatus LIKE %:searchText%)")
	Page<Request> findByDateOfCreateLessThan(@Param("denNgay") LocalDate denNgay,
			@Param("searchText") String searchText, Pageable pageable);

	/**
	 * Finds requests created on or after a given date and matching a given search
	 * text within user names, room names, or request statuses. The search is
	 * case-insensitive.
	 *
	 * @param tuNgay     The start date for the search range.
	 * @param searchText The text to search for within user names, room names, or
	 *                   request statuses.
	 * @param pageable   Pagination and sorting options for the query results.
	 * @return A Page of Request objects that match the date range and search
	 *         criteria.
	 */
	@Query("SELECT r FROM Request r WHERE r.DateOfCreate >= :tuNgay AND (r.user.name LIKE %:searchText% OR r.user.room.roomName LIKE %:searchText% OR r.requestStatus LIKE %:searchText%)")
	Page<Request> findByDateOfCreateGreaterThan(@Param("tuNgay") LocalDate tuNgay,
			@Param("searchText") String searchText, Pageable pageable);

	/**
	 * Finds a request by its unique request ID.
	 *
	 * @param requestId The ID of the request to be retrieved.
	 * @return The Request object matching the provided request ID, or null if not
	 *         found.
	 */
	Request findByRequestID(Long requestId);
}
