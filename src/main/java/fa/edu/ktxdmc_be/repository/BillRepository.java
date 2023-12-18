package fa.edu.ktxdmc_be.repository;

import fa.edu.ktxdmc_be.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Represents an BillRepository
 * 
 * @author
 */
public interface BillRepository extends JpaRepository<Bill, Long> {
	/**
	 * Retrieve a list of bills associated with a user by their user ID.
	 *
	 * @param userId The unique identifier of the user for whom to fetch bills.
	 * @return A list of Bill objects associated with the specified user ID.
	 */
	@Query("FROM Bill b WHERE b.user.userID = :userId")
	List<Bill> findByUserID(long userId);

}
