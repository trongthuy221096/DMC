package fa.edu.ktxdmc_be.dto.response;

import fa.edu.ktxdmc_be.model.Bill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private Long billID;
    private Double total;
    private String status;
    private LocalDate createdDate;
    private String billContent;
    private Long userID;

    public BillResponse(Bill bill) {
        if (bill != null) {
            BeanUtils.copyProperties(bill, this);
            this.userID = bill.getUser().getUserID();
        }
    }
}
