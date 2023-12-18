package fa.edu.ktxdmc_be.repository;

import fa.edu.ktxdmc_be.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Represents an RoomRepository
 *
 * @author
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
    /**
     * Find rooms by their area using a partial match.
     *
     * @param valueSearch The partial area name to search for.
     * @param pageable    The pageable object for pagination.
     * @return A paginated list of rooms matching the partial area name.
     */
    Page<Room> findByArea(String valueSearch, Pageable pageable);

    /**
     * Find a room by its unique room ID.
     *
     * @param roomID The unique identifier of the room to retrieve.
     * @return The room object associated with the specified room ID.
     */
    Room findByRoomID(long roomID);

    List<Room> findByRoomStatusIsNotLike(String status);

    Room findByRoomName(String roomName);

}
