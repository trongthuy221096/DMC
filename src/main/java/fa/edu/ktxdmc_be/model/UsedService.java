package fa.edu.ktxdmc_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
@IdClass(UsedServiceID.class)
public class UsedService {

    @Id
    @Column(name = "ServiceID")
    private long serviceID;

    @Id
    @Column(name = "RoomID")
    private long roomID;

    @Id
    @Column(name = "StartDate")
    private LocalDate startDate;

    @Id
    @Column(name = "EndDate")
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    private LocalDate endDate;

    @Column(name = "StartNumber")
    private int startNumber;

    @Column(name = "EndNumber")
    private int endNumber;

    @ManyToOne
    @JoinColumn(name = "ServiceID", referencedColumnName = "serviceID", insertable = false, updatable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "RoomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room room;

}
