package fa.edu.ktxdmc_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "UserMember")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "BirthDay")
    private LocalDate birthDay;

    @Column(name = "Address")
    private String address;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "CMND")
    private String cmnd;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Role")
    private String role;

    @Column(name = "isActive")
    private boolean isActive;

    @OneToMany(mappedBy = "user")
    private List<Request> listRequest;

    @OneToMany(mappedBy = "user")
    private List<Bill> listBill;

    @ManyToOne
    @JoinColumn(name = "RoomID")
    private Room room;
}
