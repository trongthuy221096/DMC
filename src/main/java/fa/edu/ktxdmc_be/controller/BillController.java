package fa.edu.ktxdmc_be.controller;

import fa.edu.ktxdmc_be.dto.request.BillRequest;
import fa.edu.ktxdmc_be.dto.response.BillResponse;
import fa.edu.ktxdmc_be.model.Bill;
import fa.edu.ktxdmc_be.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Represents an BillController
 *
 * @author
 */
@RestController
@RequestMapping("bill")
public class BillController {
    @Autowired
    private BillService billService;

    /**
     * Retrieve a list of bills associated with a specific user ID.
     *
     * @param phoneNumber The unique identifier of the user for whom to fetch bills.
     * @return ResponseEntity containing a list of BillResponse objects if
     * successful, or an exception message if an error occurs during
     * retrieval.
     */
    @GetMapping("list/{phoneNumber}")
    public ResponseEntity<?> getListBillByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            List<BillResponse> billResponseList = billService.getListBillByPhoneNumber(phoneNumber);
            return ResponseEntity.ok().body(billResponseList);
        } catch (Exception ex) {
            return ResponseEntity.ok().body(ex);
        }
    }

    /**
     * Retrieve a paginated list of bills and perform monthly bill statistics.
     *
     * @param page     The page number for the result (default is 1 if not
     *                 provided).
     * @param pageSize The number of bills per page in the result (default is 5 if
     *                 not provided).
     * @return ResponseEntity containing a paginated list of BillResponse objects
     * with monthly statistics if successful, or a "NO_CONTENT" response
     * status if there are no bills, or an "INTERNAL_SERVER_ERROR" response
     * status if an error occurs during processing.
     */
    @GetMapping("statistic")
    public ResponseEntity<?> getListBill(@RequestParam(name = "page", defaultValue = "1") int page,
                                         @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
            Page<Bill> billPage = billService.getListBillPageable(pageRequest);

            if (billPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // Chuyển đổi từ Page<Request> sang Page<RequestDTO>
            Page<BillResponse> requestDtoPage = billPage.map(BillResponse::new);

            return new ResponseEntity<>(requestDtoPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateBill(@RequestBody BillRequest billRequest) {
        try {
            billService.updateBill(billRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
