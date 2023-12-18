package fa.edu.ktxdmc_be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID")
    private Long roomID;

    @Column(name = "RoomName")
    private String roomName;

    @Column(name = "NumberOfBed")
    private int numberOfBed;

    @Column(name = "NumberOfPeople")
    private int numberOfPeople;

    @Column(name = "Price")
    private Double price;

    @Column(name = "RoomStatus")
    private String roomStatus;

    @Column(name = "Area")
    private String area;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<User> listUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    @JsonBackReference
    private List<EquipmentRoom> listEquipmentRoom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<UsedService> listUsedService;
}
