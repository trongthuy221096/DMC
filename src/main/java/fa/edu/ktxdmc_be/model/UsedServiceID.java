package fa.edu.ktxdmc_be.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter

public class UsedServiceID implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long serviceID;

    private long roomID;

    private LocalDate startDate;

    private LocalDate endDate;

    @Override
    public int hashCode() {
        return Objects.hash(endDate, roomID, serviceID, startDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsedServiceID other = (UsedServiceID) obj;
        return Objects.equals(endDate, other.endDate) && roomID == other.roomID && serviceID == other.serviceID
                && Objects.equals(startDate, other.startDate);
    }

}
