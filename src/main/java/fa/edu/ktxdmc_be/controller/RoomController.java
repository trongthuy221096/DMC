package fa.edu.ktxdmc_be.controller;

import fa.edu.ktxdmc_be.common.UserDetailsImpl;
import fa.edu.ktxdmc_be.dto.request.RoomRequest;
import fa.edu.ktxdmc_be.dto.response.MessageResponse;
import fa.edu.ktxdmc_be.dto.response.RoomResponse;
import fa.edu.ktxdmc_be.model.Room;
import fa.edu.ktxdmc_be.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Represents an RoomController
 *
 * @author
 */
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    /**
     * Retrieve a paginated list of rooms or perform a search for rooms by area.
     *
     * @param page        The page number for the result (default is 1 if not
     *                    provided).
     * @param pageSize    The number of rooms per page in the result (default is 5
     *                    if not provided).
     * @param valueSearch The value to search for rooms by area (default is an empty
     *                    string if not provided).
     * @return ResponseEntity containing a paginated list of RoomResponse objects if
     * successful, or a "NO_CONTENT" response status if there are no rooms,
     * or an "INTERNAL_SERVER_ERROR" response status if an error occurs
     * during processing.
     */
    @GetMapping("")
    public ResponseEntity<Page<RoomResponse>> getAllRequests(@RequestParam(name = "page", defaultValue = "1") int page,
                                                             @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                             @RequestParam(name = "valueSearch", defaultValue = "") String valueSearch) {
        try {
            PageRequest pageableService = PageRequest.of(page - 1, pageSize);
            Page<Room> servicePage;

            if ("".equals(valueSearch)) {
                servicePage = roomService.findAll(pageableService);
            } else {
                servicePage = roomService.findByArea(valueSearch, pageableService);
            }

            if (servicePage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Page<RoomResponse> roomResponsePage = servicePage.map(RoomResponse::new);


            return new ResponseEntity<>(roomResponsePage, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Retrieve a list of all available rooms.
     *
     * @return ResponseEntity containing a list of RoomResponse objects if
     * successful, or an "INTERNAL_SERVER_ERROR" response status if an error
     * occurs during retrieval.
     */
    @GetMapping("/listRoom")
    public ResponseEntity<?> getListRoom() {
        try {
            List<RoomResponse> roomResponseList = roomService.findAll();
            return new ResponseEntity<>(roomResponseList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Create a new room based on the provided RoomRequest object.
     *
     * @param roomRequest The RoomRequest object containing information about the
     *                    room to be created.
     * @return ResponseEntity with an "OK" status if the room is created
     * successfully, or a "BAD_REQUEST" response status if validation errors
     * occur (uncomment the validation code as needed).
     */
    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@RequestBody @Valid RoomRequest roomRequest) {
        if (roomService.createRoom(roomRequest) == 1) {
            return ResponseEntity.ok().body(new MessageResponse("successful"));
        } else if (roomService.createRoom(roomRequest) == 0) {
            return ResponseEntity.ok().body(new MessageResponse("existed"));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("failed"));
        }
    }

    /**
     * Delete a room by its unique identifier.
     *
     * @param id The unique identifier of the room to be deleted.
     * @return ResponseEntity with a "NO_CONTENT" status if the deletion is
     * successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieve details of a room by its unique identifier.
     *
     * @param id The unique identifier of the room for which to fetch details.
     * @return ResponseEntity containing a list of UserOfRoomResponse objects with
     * room details if successful, or an "INTERNAL_FORBIDDEN" response status
     * if user hasn't permission to get room detail and an "INTERNAL_SERVER_ERROR" response
     * status if an error occurs during retrieval.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomDetail(@PathVariable Long id,
                                           Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
//            List<UserOfRoomResponse> userOfRoomResponseList = roomService.findUserOfRoom(id);
            RoomResponse roomResponse = roomService.getRoomDetail(id);
            return new ResponseEntity<>(roomResponse, HttpStatus.OK);
        } else if (userDetails.getRoomId() == id) {
//            List<UserOfRoomResponse> userOfRoomResponseList = roomService.findUserOfRoom(id);
            RoomResponse roomResponse = roomService.getRoomDetail(id);
            return new ResponseEntity<>(roomResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("validRooms")
    public ResponseEntity<?> getValidRooms() {
        try {
            List<RoomResponse> roomResponseList = roomService.getValidRooms();
            return new ResponseEntity<>(roomResponseList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

