package fa.edu.ktxdmc_be.service;

import fa.edu.ktxdmc_be.dto.request.BillRequest;
import fa.edu.ktxdmc_be.dto.response.BillResponse;
import fa.edu.ktxdmc_be.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Represents an BillService
 *
 * @author
 */
public interface BillService {
    /**
     * Retrieve a list of all bills.
     *
     * @return A list of all bill objects.
     */
    List<Bill> getListBill();

    /**
     * Retrieve a list of bills associated with a user by their user ID.
     *
     * @param phoneNumber The unique identifier of the user for whom to fetch bills.
     * @return A list of Bill objects associated with the specified user ID.
     */
    List<BillResponse> getListBillByPhoneNumber(String phoneNumber);

    /**
     * Create a bill for a new user.
     *
     * @param bill The bill object to create for the new user.
     */
    void createBillForNewUser(Bill bill);

    /**
     * Retrieve a paginated list of statistics for bills using a pageable object.
     *
     * @param pageRequest The pageable object for pagination.
     * @return A paginated list of Bill objects representing statistics for bills.
     */
    Page<Bill> getListBillPageable(PageRequest pageRequest);

    void updateBill(BillRequest billRequest);
}
