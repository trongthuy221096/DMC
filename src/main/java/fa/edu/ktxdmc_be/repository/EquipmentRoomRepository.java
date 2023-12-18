package fa.edu.ktxdmc_be.repository;

import fa.edu.ktxdmc_be.model.EquipmentRoom;
import fa.edu.ktxdmc_be.model.EquipmentRoomID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRoomRepository extends JpaRepository<EquipmentRoom, EquipmentRoomID> {
    /**
     * Finds an EquipmentRoom entity by matching the equipment ID and room ID.
     *
     * @param equipmentId The unique identifier of the equipment.
     * @param roomId      The unique identifier of the room.
     * @return The EquipmentRoom entity if found, or null if not found.
     */
    EquipmentRoom findByEquipmentIDAndRoomID(Long equipmentId, Long roomId);

    /**
     * Deletes an EquipmentRoom entity by matching the equipment ID and room ID.
     *
     * @param equipmentId The unique identifier of the equipment to be removed from
     *                    the room.
     * @param roomId      The unique identifier of the room from which the equipment
     *                    should be removed.
     */
    void deleteByEquipmentIDAndRoomID(Long equipmentId, Long roomId);

    /**
     * Finds a list of EquipmentRoom entities associated with a specific room based
     * on the room's ID.
     *
     * @param roomId The unique identifier of the room for which to retrieve
     *               equipment associations.
     * @return A list of EquipmentRoom entities associated with the specified room.
     */
    @Query("SELECT er FROM EquipmentRoom er WHERE er.room.roomID = :roomId")
    List<EquipmentRoom> findByRoomId(@Param("roomId") Long roomId);
}
