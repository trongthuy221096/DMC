package fa.edu.ktxdmc_be.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import fa.edu.ktxdmc_be.model.Service;

/**
 * Represents an RequestService
 * 
 * @author ThuyTT77
 */
public interface ServiceService {

	/**
	 * Retrieve a paginated list of all service items using the provided
	 * PageRequest.
	 *
	 * @param pageService The PageRequest object for pagination.
	 * @return A paginated list of service items.
	 */
	Page<Service> findAll(PageRequest pageService);

	/**
	 * Update a service item in the database.
	 *
	 * @param service The service item to update.
	 * @return A boolean indicating whether the update was successful (true) or not
	 *         (false).
	 */
	boolean updateService(Service service);

	/**
	 * Find service items by their service name using a custom JPQL query with text
	 * search and pagination.
	 *
	 * @param searchText The text to search for in service names.
	 * @param pageable   The Pageable object for pagination.
	 * @return A paginated list of service items matching the search criteria.
	 */
	Page<Service> findByServiceName(String searchText, Pageable pageable);

	/**
	 * Find service items by their price using a custom JPQL query with price search
	 * and pagination.
	 *
	 * @param price    The price value to search for in service items.
	 * @param pageable The Pageable object for pagination.
	 * @return A paginated list of service items matching the search criteria.
	 */
	Page<Service> findByPrice(Double price, Pageable pageable);
}
