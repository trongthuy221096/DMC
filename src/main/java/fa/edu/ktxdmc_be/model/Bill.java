package fa.edu.ktxdmc_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillID")
    private Long billID;

    @Column(name = "Total")
    private Double total;

    @Column(name = "Status")
    private String status;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Column(name = "BillContent")
    private String billContent;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
}
