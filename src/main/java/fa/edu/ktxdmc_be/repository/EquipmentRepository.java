package fa.edu.ktxdmc_be.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fa.edu.ktxdmc_be.model.Equipment;

/**
 * Represents an EquipmentRepository
 * @author 
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
	/**
	 * Find an equipment by its unique equipment ID.
	 *
	 * @param id The unique identifier of the equipment to retrieve.
	 * @return The equipment object associated with the specified equipment ID.
	 */
	Equipment findByEquipmentID(Long id);

	/**
	 * Find equipment by a partial match of their equipment name.
	 *
	 * @param equipmentName The partial name of the equipment to search for.
	 * @return A list of equipment objects containing the specified partial
	 *         equipment name.
	 */
	List<Equipment> findByEquipmentNameContaining(String equipmentName);

	/**
	 * Find equipment by equipment name using a custom JPQL query with text search.
	 *
	 * @param searchText The text to search for in equipment names.
	 * @param pageable   The pageable object for pagination.
	 * @return A paginated list of equipment objects matching the search criteria.
	 */
	@Query("SELECT s FROM Equipment s WHERE s.equipmentName LIKE %:searchText%")
	Page<Equipment> findByEquipmentName(@Param("searchText") String searchText, Pageable pageable);
}
