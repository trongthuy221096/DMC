package fa.edu.ktxdmc_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ServiceID")
    private Long serviceID;

    @Column(name = "ServiceName")
    private String serviceName;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Unit")
    private String unit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private List<UsedService> listUsedService;
}
