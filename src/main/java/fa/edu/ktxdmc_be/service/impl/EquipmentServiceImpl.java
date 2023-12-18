package fa.edu.ktxdmc_be.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fa.edu.ktxdmc_be.dto.response.EquipmentResponse;
import fa.edu.ktxdmc_be.model.Equipment;
import fa.edu.ktxdmc_be.repository.EquipmentRepository;
import fa.edu.ktxdmc_be.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {
	private final ModelMapper mapper;

	private final EquipmentRepository equipmentRepository;

	@Autowired
	public EquipmentServiceImpl(EquipmentRepository equipmentRepository, ModelMapper mapper) {
		this.equipmentRepository = equipmentRepository;
		this.mapper = mapper;
	}

	@Override
	public Equipment findByEquipmentID(Long id) {
		return equipmentRepository.findByEquipmentID(id);
	}

	@Override
	public Equipment save(Equipment equipment) {
		return equipmentRepository.save(equipment);
	}

	@Override
	public void deleteById(Long id) {
		equipmentRepository.deleteById(id);
	}

	@Override
	public List<Equipment> findByEquipmentNameContaining(String equipmentName) {
		return equipmentRepository.findByEquipmentNameContaining(equipmentName);
	}

	@Override
	public Page<Equipment> findAll(PageRequest pageEquipment) {
		return equipmentRepository.findAll(pageEquipment);
	}

	@Override
	public Page<Equipment> findByEquipmentName(String searchText, Pageable pageable) {
		return equipmentRepository.findByEquipmentName(searchText, pageable);
	}

	@Override
	public List<EquipmentResponse> findAll() {
		List<Equipment> equipmentList = equipmentRepository.findAll();
		return equipmentList.stream().map(equipment -> mapper.map(equipment, EquipmentResponse.class))
				.collect(Collectors.toList());
	}

}
