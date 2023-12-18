package fa.edu.ktxdmc_be.dto.response;

import org.springframework.beans.BeanUtils;

import fa.edu.ktxdmc_be.model.EquipmentRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentRoomResponse {
	private Long equipmentID;
	private Long roomID;
	private int originalQuantity;
	private int curentQuatity;
	private String roomName;
	private String equipmentName;
	
	public EquipmentRoomResponse(EquipmentRoom equipmentRoom) {
		if (equipmentRoom != null) {
			BeanUtils.copyProperties(equipmentRoom, this);
			this.roomName = equipmentRoom.getRoom().getRoomName();
			this.equipmentName = equipmentRoom.getEquipment().getEquipmentName();
		}
	}
}
