package fa.edu.ktxdmc_be.repository;

import fa.edu.ktxdmc_be.model.UsedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an UsedServiceRepository
 *
 * @author
 */
public interface UsedServiceRepository extends JpaRepository<UsedService, Long> {
    List<UsedService> findByEndDate(LocalDate endDate);
}
