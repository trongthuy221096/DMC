package fa.edu.ktxdmc_be.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter

public class EquipmentRoomID implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long equipmentID;

	private Long roomID;

	public EquipmentRoomID() {
		// TODO Auto-generated constructor stub
	}

	public EquipmentRoomID(Long equipmentId, Long roomId) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(equipmentID, roomID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentRoomID other = (EquipmentRoomID) obj;
		return Objects.equals(equipmentID, other.equipmentID) && Objects.equals(roomID, other.roomID);
	}

}
