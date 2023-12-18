package fa.edu.ktxdmc_be.service.impl;

import fa.edu.ktxdmc_be.dto.request.RoomRequest;
import fa.edu.ktxdmc_be.dto.response.RoomResponse;
import fa.edu.ktxdmc_be.dto.response.UserOfRoomResponse;
import fa.edu.ktxdmc_be.model.Room;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.repository.RoomRepository;
import fa.edu.ktxdmc_be.repository.UserRepository;
import fa.edu.ktxdmc_be.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper mapper, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public void save() {
    }

    @Override
    public List<RoomResponse> findAll() {
        List<Room> roomList = roomRepository.findAll();
        return roomList.stream().map(RoomResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getValidRooms() {
        List<Room> roomList = roomRepository.findByRoomStatusIsNotLike("full");
        return roomList.stream().map(RoomResponse::new).collect(Collectors.toList());
    }

    @Override
    public int createRoom(RoomRequest roomRequest) {
        try {
            Room room = roomRepository.findByRoomName(roomRequest.getRoomName());
            if (room == null) {
                Room newRoom = mapper.map(roomRequest, Room.class);
                roomRepository.save(newRoom);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void deleteById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow();
        if (room.getNumberOfPeople() > 0) {
            throw new RuntimeException("Rooms with tenants cannot be deleted!");
        }
        roomRepository.deleteById(id);
    }

    @Override
    public Page<Room> findAll(PageRequest pageRequest) {
        return roomRepository.findAll(pageRequest);
    }

    @Override
    public Page<Room> findByArea(String valueSearch, Pageable pageable) {
        return roomRepository.findByArea(valueSearch, pageable);
    }

    @Override
    public List<UserOfRoomResponse> findUserOfRoom(Long id) {
        List<User> userList = userRepository.findUsersOfRoom(id);
        return userList.stream().map(user -> mapper.map(user, UserOfRoomResponse.class)).collect(Collectors.toList());
    }

    @Override
    public RoomResponse getRoomDetail(long id) {
        Room room = roomRepository.findByRoomID(id);
        return new RoomResponse(room);
    }
}
