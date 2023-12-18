package fa.edu.ktxdmc_be.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import fa.edu.ktxdmc_be.dto.response.EquipmentResponse;
import fa.edu.ktxdmc_be.model.Equipment;


/**
 * Represents an EquipmentService
 * @author 
 */
public interface EquipmentService {
	/**
	 * Retrieve a list of all equipment items.
	 *
	 * @return A list containing all equipment items.
	 */
	List<EquipmentResponse> findAll();

	/**
	 * Retrieve a paginated list of all equipment items using the provided
	 * PageRequest.
	 *
	 * @param pageService The PageRequest object for pagination.
	 * @return A paginated list of equipment items.
	 */
	Page<Equipment> findAll(PageRequest pageService);

	/**
	 * Find an equipment item by its unique ID.
	 *
	 * @param id The unique identifier of the equipment item to retrieve.
	 * @return The equipment item associated with the specified ID.
	 */
	Equipment findByEquipmentID(Long id);


	/**
	 * Save or update an equipment item in the database.
	 *
	 * @param equipment The equipment item to save or update.
	 * @return The saved or updated equipment item.
	 */
	Equipment save(Equipment equipment);



	/**
	 * Delete an equipment item from the database by its ID.
	 *
	 * @param id The unique identifier of the equipment item to delete.
	 */
	void deleteById(Long id);

	/**
	 * Find equipment items whose names contain the specified equipmentName.
	 *
	 * @param equipmentName The text to search for in equipment names.
	 * @return A list of equipment items matching the search criteria.
	 */
	List<Equipment> findByEquipmentNameContaining(String equipmentName);

	/**
	 * Find equipment items by their equipment name using a custom JPQL query with
	 * text search and pagination.
	 *
	 * @param searchText The text to search for in equipment names.
	 * @param pageable   The Pageable object for pagination.
	 * @return A paginated list of equipment items matching the search criteria.
	 */
	Page<Equipment> findByEquipmentName(String searchText, Pageable pageable);
}
