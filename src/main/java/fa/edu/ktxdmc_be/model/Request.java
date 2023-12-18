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
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequestID")
    private Long requestID;

    @Column(name = "RequestStatus")
    private String requestStatus;

    @Column(name = "RequestContent")
    private String requestContent;

    @Column(name = "DateOfCreate")
    private LocalDate DateOfCreate;

    @Column(name = "DateOfUpdate")
    private LocalDate dateOfUpdate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
}
