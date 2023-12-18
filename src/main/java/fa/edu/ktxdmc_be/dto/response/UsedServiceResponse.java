package fa.edu.ktxdmc_be.dto.response;

import fa.edu.ktxdmc_be.model.UsedService;
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
public class UsedServiceResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private int startNumber;
    private int endNumber;
    private double price;
    private String name;

    public UsedServiceResponse(UsedService usedService) {
        if (usedService != null) {
            BeanUtils.copyProperties(usedService, this);
            this.price = usedService.getService().getPrice();
            this.name = usedService.getService().getServiceName();
        }
    }
}
