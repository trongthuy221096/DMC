package fa.edu.ktxdmc_be.dto.response;

import fa.edu.ktxdmc_be.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class RoomResponse {
    private Long roomID;

    private String roomName;

    private int numberOfBed;

    private int numberOfPeople;

    private long numberOfRequest;

    private Double price;

    private String roomStatus;

    private String area;
    private List<UsedServiceResponse> usedService;
    private List<UserOfRoomResponse> users;
    private List<EquipmentRoomResponse> equipments;

    public RoomResponse(Room room) {
        if (room != null) {
            BeanUtils.copyProperties(room, this);
            this.numberOfRequest = room.getListUser().stream().filter(user -> {
                return !user.isActive();
            }).count();
            this.usedService = room.getListUsedService().stream()
                    .map(UsedServiceResponse::new)
                    .collect(Collectors.toList());
            this.users = room.getListUser().stream()
                    .map(UserOfRoomResponse::new)
                    .collect(Collectors.toList());
            this.equipments = room.getListEquipmentRoom().stream()
                    .map(EquipmentRoomResponse::new)
                    .collect(Collectors.toList());
        }
    }
}
