package fa.edu.ktxdmc_be.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fa.edu.ktxdmc_be.dto.response.EquipmentRoomResponse;
import fa.edu.ktxdmc_be.model.EquipmentRoom;
import fa.edu.ktxdmc_be.service.EquipmentRoomService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("")
public class EquipmentRoomController {
	@Autowired
	private EquipmentRoomService equipmentRoomService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/equipmentroom")
	public ResponseEntity<Page<EquipmentRoomResponse>> getAllRequests(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(name = "valueSearch", defaultValue = "") String valueSearch) {
		try {
			PageRequest pageableService = PageRequest.of(page - 1, pageSize);
			Page<EquipmentRoom> servicePage = null;

			if ("".equals(valueSearch)) {
				servicePage = equipmentRoomService.findAll(pageableService);
			}

			if (servicePage.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			// Chuyển đổi từ Page<Request> sang Page<RequestDTO>
			Page<EquipmentRoomResponse> requestDtoPage = servicePage.map(EquipmentRoomResponse::new);

			return new ResponseEntity<>(requestDtoPage, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/equipmentroom")
	public ResponseEntity<EquipmentRoom> createEquipmentRoom(@RequestBody EquipmentRoom equipmentRoom) {
		try {
			EquipmentRoom _equipment = equipmentRoomService.save(new EquipmentRoom(equipmentRoom.getEquipmentID(),
					equipmentRoom.getRoomID(), equipmentRoom.getOriginalQuantity(), equipmentRoom.getCurentQuatity()));
			return new ResponseEntity<>(_equipment, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/equipmentroom")
	public ResponseEntity<EquipmentRoom> updateEquipmentRoom(@RequestBody EquipmentRoom equipmentRoom) {
		try {
			EquipmentRoom _equipmentRoom = equipmentRoomService
					.findByEquipmentIdAndRoomId(equipmentRoom.getEquipmentID(), equipmentRoom.getRoomID());
			_equipmentRoom.setEquipmentID(equipmentRoom.getEquipmentID());
			_equipmentRoom.setRoomID(equipmentRoom.getRoomID());
			_equipmentRoom.setOriginalQuantity(equipmentRoom.getOriginalQuantity());
			_equipmentRoom.setCurentQuatity(equipmentRoom.getCurentQuatity());
			return new ResponseEntity<>(equipmentRoomService.save(_equipmentRoom), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/equipmentroom")
	public ResponseEntity<HttpStatus> deleteEquipmentRoom(@RequestParam Long equipmentId, @RequestParam Long roomId) {
		try {
			// EquipmentRoomID id = new EquipmentRoomID(equipmentId,roomId);
			equipmentRoomService.deleteByEquipmentIDAndRoomID(equipmentId, roomId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get the list of devices in the room by room id
	 *
	 * @param id The id of the room to get the device list
	 * @return a list of EquipmentRoomDTO objects containing equipment information
	 *         in the room
	 */
	@GetMapping("/equipmentroom/{id}")
	public List<EquipmentRoomResponse> getEquipmentsByRoomId(@PathVariable("id") Long id) {
		return equipmentRoomService.findEquipmentsByRoomId(id).stream().map(room -> {
			EquipmentRoomResponse dto = new EquipmentRoomResponse();
			dto.setRoomID(room.getRoom().getRoomID());
			dto.setRoomName(room.getRoom().getRoomName());

			dto.setEquipmentID(room.getEquipment().getEquipmentID());
			dto.setEquipmentName(room.getEquipment().getEquipmentName());

			dto.setOriginalQuantity(room.getOriginalQuantity());
			dto.setCurentQuatity(room.getCurentQuatity());

			return dto;
		}).collect(Collectors.toList());
	}
}
