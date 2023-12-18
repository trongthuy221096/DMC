package fa.edu.ktxdmc_be.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import fa.edu.ktxdmc_be.model.Equipment;
import fa.edu.ktxdmc_be.model.EquipmentRoom;

@Transactional
public interface EquipmentRoomService {
	/**
	 * Retrieve all EquipmentRooms.
	 *
	 * @return A list of all EquipmentRooms.
	 */
	List<EquipmentRoom> findAll();

	/**
	 * Retrieve a page of EquipmentRooms.
	 *
	 * @param pageService The pagination information.
	 * @return A page of EquipmentRooms.
	 */
	Page<EquipmentRoom> findAll(PageRequest pageService);

	/**
	 * Find an EquipmentRoom by its equipment ID and room ID.
	 *
	 * @param equipmentId The ID of the equipment.
	 * @param roomId      The ID of the room.
	 * @return The EquipmentRoom matching the provided IDs, or null if not found.
	 */
	EquipmentRoom findByEquipmentIdAndRoomId(Long equipmentId, Long roomId);

	/**
	 * Save an EquipmentRoom entity.
	 *
	 * @param equipmentRoom The EquipmentRoom to save.
	 * @return The saved EquipmentRoom.
	 */
	EquipmentRoom save(EquipmentRoom equipmentRoom);

	/**
	 * Find a page of EquipmentRooms by equipment name.
	 *
	 * @param searchText The text to search for in equipment names.
	 * @param pageable   The pagination information.
	 * @return A page of EquipmentRooms matching the search criteria.
	 */
	Page<EquipmentRoom> findByEquipmentName(String searchText, Pageable pageable);

	/**
	 * Delete an EquipmentRoom by its equipment ID and room ID.
	 *
	 * @param equipmentId The ID of the equipment.
	 * @param roomId      The ID of the room.
	 */
	void deleteByEquipmentIDAndRoomID(Long equipmentId, Long roomId);

	/**
	 * Find a list of EquipmentRooms by room ID.
	 *
	 * @param roomId The ID of the room.
	 * @return A list of EquipmentRooms associated with the provided room ID.
	 */
	List<EquipmentRoom> findEquipmentsByRoomId(Long roomId);
}
