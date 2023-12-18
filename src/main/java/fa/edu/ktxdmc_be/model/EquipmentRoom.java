package fa.edu.ktxdmc_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@IdClass(EquipmentRoomID.class)
public class EquipmentRoom {
    @Id
    @Column(name = "EquipmentID")
    private Long equipmentID;

    @Id
    @Column(name = "RoomID")
    private Long roomID;

    @Column(name = "OriginalQuantity")
    private int originalQuantity;

    @Column(name = "CurentQuatity")
    private int curentQuatity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "RoomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room room;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "EquipmentID", referencedColumnName = "equipmentID", insertable = false, updatable = false)
    private Equipment equipment;

    public EquipmentRoom(Long equipmentID, Long roomID, int originalQuantity, int curentQuatity) {
        super();
        this.equipmentID = equipmentID;
        this.roomID = roomID;
        this.originalQuantity = originalQuantity;
        this.curentQuatity = curentQuatity;
    }

}
