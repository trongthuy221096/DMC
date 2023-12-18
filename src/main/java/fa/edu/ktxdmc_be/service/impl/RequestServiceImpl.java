package fa.edu.ktxdmc_be.service.impl;

import fa.edu.ktxdmc_be.common.EmailDetails;
import fa.edu.ktxdmc_be.dto.request.RequestCreateRequest;
import fa.edu.ktxdmc_be.dto.request.RequestUpdateRequest;
import fa.edu.ktxdmc_be.model.Request;
import fa.edu.ktxdmc_be.model.Room;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.repository.RequestRepository;
import fa.edu.ktxdmc_be.repository.RoomRepository;
import fa.edu.ktxdmc_be.service.CustomUserDetailsService;
import fa.edu.ktxdmc_be.service.EmailService;
import fa.edu.ktxdmc_be.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Represents an RequestServiceImpl
 *
 * @author ThuyTT77 1996-10-22
 */
@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepo;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public void saveRequest(RequestCreateRequest request) {
        Request newRequest = new Request();
        newRequest.setDateOfCreate(LocalDate.now());
        newRequest.setRequestContent(request.getRequestContent());
        newRequest.setRequestStatus(request.getRequestStatus());
        newRequest.setUser(userDetailsService.getUserByUserId(request.getUserID()));

        requestRepo.save(newRequest);
    }

    @Override
    public long getUserIDByRequestID(long requestID) {
        Request request = requestRepo.findByRequestID(requestID);

        return request.getUser().getUserID();
    }

    @Override
    public Request findRequestByID(long requestID) {
        return requestRepo.findById(requestID).orElse(null);
    }

    /**
     * @param searchValue
     * @param pageRequest
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public Page<Request> findByUserOrRoomOrRequestStatus(String searchValue, PageRequest pageRequest) {
        return requestRepo.findByUserOrRoomOrRequestStatus(searchValue, pageRequest);
    }

    /**
     * @param requestUpdateRequest
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public boolean updateRequestStatus(RequestUpdateRequest requestUpdateRequest) {
        // Kiểm tra xem yêu cầu có tồn tại không
        Request request = requestRepo.findById(requestUpdateRequest.getId()).orElse(null);

        if (request == null) {
            return false; // Không tìm thấy yêu cầu
        }

        if (request.getRequestContent().equals("create")) {
            User user = request.getUser();
            Room room = roomRepository.findByRoomID(user.getRoom().getRoomID());
            room.setNumberOfPeople(room.getNumberOfPeople() + 1);
            if (room.getNumberOfPeople() == room.getNumberOfBed()) {
                room.setRoomStatus("full");
            }
            if (room.getNumberOfPeople() > 0) {
                room.setRoomStatus("valid");
            }

            String userPassword = userDetailsService.initPasswordUser(user.getUserID());
            roomRepository.save(room);
            if (requestUpdateRequest.getRequestStatus().equals("accepted")) {
                EmailDetails emailDetails = new EmailDetails();
//                User userDetail = userDetailsService.getUserByUserId(getUserIDByRequestID(requestUpdateRequest.getId()));
                emailDetails.setRecipient(user.getEmail());
                emailDetails.setSubject("Thông báo xác nhận đơn đăng ký phòng ký túc xá DMC Đà Nẵng");
                emailDetails.setMsgBody(
                        "Username: Số điện thoại đăng ký\n" +
                                "Password:" + userPassword
                );
                emailService.sendSimpleMail(emailDetails);
            } else if (requestUpdateRequest.getRequestStatus().equals("refused")) {
                EmailDetails emailDetails = new EmailDetails();
//                User user = userDetailsService.getUserByUserId(getUserIDByRequestID(requestUpdateRequest.getId()));
                emailDetails.setRecipient(user.getEmail());
                emailDetails.setSubject("Thông báo xác nhận đơn đăng ký phòng ký túc xá DMC Đà Nẵng");
                emailDetails.setMsgBody(
                        "Yêu cầu đăng ký phòng ký túc xá của bạn đã bị từ chối!!!"
                );
                emailService.sendSimpleMail(emailDetails);
            }
        }


        // Cập nhật trạng thái yêu cầu
        request.setRequestStatus(requestUpdateRequest.getRequestStatus());
        request.setDateOfUpdate(LocalDate.now());
        requestRepo.save(request);

        return true;
    }

    /**
     * @param pageRequest
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public Page<Request> findAll(PageRequest pageRequest) {
        return requestRepo.findAll(pageRequest);
    }

    /**
     * @param tuNgay
     * @param denNgay
     * @param searchText
     * @param pageRequest
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public Page<Request> findByDateOfCreateAndUserOrRoomOrRequestStatus(LocalDate tuNgay, LocalDate denNgay,
                                                                        String searchText, PageRequest pageRequest) {
        return requestRepo.findByDateOfCreateAndUserOrRoomOrRequestStatus(tuNgay, denNgay, searchText, pageRequest);
    }

    /**
     * @param denNgay
     * @param searchText
     * @param pageRequest
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public Page<Request> findByDateOfCreateLessThan(LocalDate denNgay, String searchText, PageRequest pageRequest) {
        return requestRepo.findByDateOfCreateLessThan(denNgay, searchText, pageRequest);
    }

    /**
     * @param tuNgay
     * @param searchText
     * @param pageRequest
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public Page<Request> findByDateOfCreateGreaterThan(LocalDate tuNgay, String searchText, PageRequest pageRequest) {
        return requestRepo.findByDateOfCreateGreaterThan(tuNgay, searchText, pageRequest);
    }

    /**
     * @param requestID
     * @return
     * @author ThuyTT77 1996-10-22
     */
    @Override
    public boolean deleteRequest(Long requestID) {
        Request request = requestRepo.findById(requestID).orElse(null);
        if (request != null) {
            requestRepo.deleteById(requestID);
            return true;
        }
        return false;
    }
}
