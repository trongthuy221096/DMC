package fa.edu.ktxdmc_be.dto.response;

import org.springframework.beans.BeanUtils;

import fa.edu.ktxdmc_be.model.Equipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentResponse {
	private Long equipmentID;
	private String equipmentName;
	private String EquipmentInfomation;

	public EquipmentResponse(Equipment equipment) {
		if (equipment != null) {
			BeanUtils.copyProperties(equipment, this);

		}
	}
}
