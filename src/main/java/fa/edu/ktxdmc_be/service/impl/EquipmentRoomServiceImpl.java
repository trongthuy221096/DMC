package fa.edu.ktxdmc_be.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fa.edu.ktxdmc_be.dto.response.EquipmentResponse;
import fa.edu.ktxdmc_be.model.Equipment;
import fa.edu.ktxdmc_be.model.EquipmentRoom;
import fa.edu.ktxdmc_be.repository.EquipmentRoomRepository;
import fa.edu.ktxdmc_be.service.EquipmentRoomService;

@Service
@Transactional
public class EquipmentRoomServiceImpl implements EquipmentRoomService {
	@Autowired
	private EquipmentRoomRepository equipmentRoomRepository;

	@Override
	public Page<EquipmentRoom> findAll(PageRequest pageService) {
		// TODO Auto-generated method stub
		return equipmentRoomRepository.findAll(pageService);
	}

	@Override
	public EquipmentRoom findByEquipmentIdAndRoomId(Long equipmentId, Long roomId) {
		// TODO Auto-generated method stub
		return equipmentRoomRepository.findByEquipmentIDAndRoomID(equipmentId, roomId);
	}

	@Override
	public EquipmentRoom save(EquipmentRoom equipmentRoom) {
		// TODO Auto-generated method stub
		return equipmentRoomRepository.save(equipmentRoom);
	}

	@Override
	public Page<EquipmentRoom> findByEquipmentName(String searchText, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByEquipmentIDAndRoomID(Long equipmentId, Long roomId) {
		equipmentRoomRepository.deleteByEquipmentIDAndRoomID(equipmentId, roomId);

	}

	@Override
	public List<EquipmentRoom> findEquipmentsByRoomId(Long roomId) {
		return equipmentRoomRepository.findByRoomId(roomId);
	}

	@Override
	public List<EquipmentRoom> findAll() {
		// TODO Auto-generated method stub
		return equipmentRoomRepository.findAll();
	}

}
