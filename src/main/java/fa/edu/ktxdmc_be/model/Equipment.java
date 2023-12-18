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
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EquipmentID")
    private Long equipmentID;

    @Column(name = "EquipmentName")
    private String equipmentName;

    @Column(name = "EquipmentInfomation")
    private String EquipmentInfomation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipment")
    @JsonBackReference
    private List<EquipmentRoom> listEquipmentRoom;

    public Equipment(String equipmentName, String equipmentInfomation) {
        super();
        this.equipmentName = equipmentName;
        EquipmentInfomation = equipmentInfomation;
    }

    @Override
    public String toString() {
        return "Equipment [equipmentID=" + equipmentID + ", equipmentName=" + equipmentName + ", EquipmentInfomation="
                + EquipmentInfomation + "]";
    }

}
