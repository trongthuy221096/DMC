package fa.edu.ktxdmc_be.service.impl;

import fa.edu.ktxdmc_be.dto.request.UsedServiceRequest;
import fa.edu.ktxdmc_be.dto.response.RoomResponse;
import fa.edu.ktxdmc_be.dto.response.UsedServiceResponse;
import fa.edu.ktxdmc_be.model.Bill;
import fa.edu.ktxdmc_be.model.Room;
import fa.edu.ktxdmc_be.model.UsedService;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.repository.BillRepository;
import fa.edu.ktxdmc_be.repository.RoomRepository;
import fa.edu.ktxdmc_be.repository.ServiceRepository;
import fa.edu.ktxdmc_be.repository.UsedServiceRepository;
import fa.edu.ktxdmc_be.service.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsedServiceServiceImpl implements UsedServiceService {
    @Autowired
    private UsedServiceRepository usedServiceRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BillRepository billRepository;

    @Override
    public void createUsedService(UsedServiceRequest usedServiceRequest) {
        UsedService dienService = new UsedService();
        dienService.setServiceID(serviceRepository.findByServiceName("dien").getServiceID());
        dienService.setService(serviceRepository.findByServiceName("dien"));
        dienService.setRoomID(usedServiceRequest.getRoomID());
        dienService.setStartNumber(usedServiceRequest.getDien_startNumber());
        dienService.setEndNumber(usedServiceRequest.getDien_endNumber());
        dienService.setStartDate(usedServiceRequest.getStartDate());
        dienService.setEndDate(usedServiceRequest.getEndDate());
        usedServiceRepository.save(dienService);
        double dienPrice = (dienService.getEndNumber() - dienService.getStartNumber()) * dienService.getService().getPrice();


        UsedService nuocService = new UsedService();
        nuocService.setServiceID(serviceRepository.findByServiceName("nuoc").getServiceID());
        nuocService.setService(serviceRepository.findByServiceName("nuoc"));
        nuocService.setRoomID(usedServiceRequest.getRoomID());
        nuocService.setStartNumber(usedServiceRequest.getNuoc_startNumber());
        nuocService.setEndNumber(usedServiceRequest.getNuoc_endNumber());
        nuocService.setStartDate(usedServiceRequest.getStartDate());
        nuocService.setEndDate(usedServiceRequest.getEndDate());
        usedServiceRepository.save(nuocService);
        double nuocPrice = (nuocService.getEndNumber() - nuocService.getStartNumber()) * nuocService.getService().getPrice();

        Room room = roomRepository.findByRoomID(usedServiceRequest.getRoomID());
        double roomPrice = room.getPrice();

        for (User user :
                room.getListUser()) {
            Bill roomBill = new Bill();
            roomBill.setUser(user);
            roomBill.setTotal(roomPrice);
            roomBill.setCreatedDate(LocalDate.now());
            roomBill.setBillContent("roomBill: " + usedServiceRequest.getEndDate());
            roomBill.setStatus("false");

            billRepository.save(roomBill);

            Bill serviceBill = new Bill();
            serviceBill.setUser(user);
            serviceBill.setTotal(dienPrice + nuocPrice);
            serviceBill.setCreatedDate(LocalDate.now());
            serviceBill.setBillContent("serviceBill: " + usedServiceRequest.getEndDate());
            serviceBill.setStatus("false");

            billRepository.save(serviceBill);

        }
    }

    @Override
    public List<RoomResponse> getListRoomUsedServiceByEndDate(LocalDate endDate) {
        List<Room> roomList = roomRepository.findAll();

        List<Room> roomListByUsedService = new ArrayList<>();

        for (Room room :
                roomList) {
            if (!room.getRoomStatus().equals("empty")) {
                List<UsedService> newUsedServiceList = new ArrayList<>();

                for (UsedService usedService :
                        room.getListUsedService()) {
                    if (usedService.getEndDate().getMonth().equals(endDate.getMonth())) {
                        newUsedServiceList.add(usedService);
                    }
                }
                room.setListUsedService(newUsedServiceList);
                roomListByUsedService.add(room);
            }
        }

        return roomListByUsedService.stream()
                .map(RoomResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsedServiceResponse> getListUsedServiceByEndDate(LocalDate endDate) {
        List<UsedService> usedServices = usedServiceRepository.findByEndDate(endDate);

        return usedServices.stream().map(UsedServiceResponse::new).collect(Collectors.toList());
    }
}
