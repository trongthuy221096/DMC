package fa.edu.ktxdmc_be.service;

import fa.edu.ktxdmc_be.dto.request.RoomRequest;
import fa.edu.ktxdmc_be.dto.response.RoomResponse;
import fa.edu.ktxdmc_be.dto.response.UserOfRoomResponse;
import fa.edu.ktxdmc_be.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Represents an RoomService
 *
 * @author
 */
public interface RoomService {

    void save();

    List<RoomResponse> findAll();

    List<RoomResponse> getValidRooms();

    int createRoom(RoomRequest roomRequest);

    void deleteById(Long id);

    /**
     * Retrieve a paginated list of all rooms using the provided PageRequest.
     *
     * @param pageRequest The PageRequest object for pagination.
     * @return A paginated list of room objects.
     */
    Page<Room> findAll(PageRequest pageRequest);

    /**
     * Find rooms by their area using a custom JPQL query with text search and
     * pagination.
     *
     * @param valueSearch The text to search for in room areas.
     * @param pageable    The Pageable object for pagination.
     * @return A paginated list of room objects matching the search criteria.
     */
    Page<Room> findByArea(String valueSearch, Pageable pageable);

    /**
     * Find users associated with a specific room by its ID.
     *
     * @param id The unique identifier of the room for which to fetch users.
     * @return A list of UserOfRoomResponse objects representing users associated
     * with the specified room.
     */
    List<UserOfRoomResponse> findUserOfRoom(Long id);

    RoomResponse getRoomDetail(long id);

}
