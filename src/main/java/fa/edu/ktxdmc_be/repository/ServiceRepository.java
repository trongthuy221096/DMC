package fa.edu.ktxdmc_be.repository;

import fa.edu.ktxdmc_be.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Represents an RequestRepo
 *
 * @author ThuyTT77
 */
public interface ServiceRepository extends JpaRepository<Service, Long> {
    /**
     * Find services by their service name using a custom JPQL query with text
     * search.
     *
     * @param searchText The text to search for in service names.
     * @param pageable   The pageable object for pagination.
     * @return A paginated list of service objects matching the search criteria.
     */
    @Query("SELECT s FROM Service s WHERE s.serviceName LIKE %:searchText%")
    Page<Service> findByServiceName(@Param("searchText") String searchText, Pageable pageable);

    /**
     * Find services by their price.
     *
     * @param price    The price value to search for.
     * @param pageable The pageable object for pagination.
     * @return A paginated list of service objects matching the specified price.
     */
    Page<Service> findByPrice(Double price, Pageable pageable);
    
    /**
     * Finds a service by its unique service name.
     *
     * @param name The unique name of the service to search for.
     * @return The Service entity if found, or null if no service with the specified name is found.
     */
    Service findByServiceName(String name);
}
