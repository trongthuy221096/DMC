package fa.edu.ktxdmc_be.service.impl;

import fa.edu.ktxdmc_be.dto.request.BillRequest;
import fa.edu.ktxdmc_be.dto.response.BillResponse;
import fa.edu.ktxdmc_be.model.Bill;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.repository.BillRepository;
import fa.edu.ktxdmc_be.repository.RoomRepository;
import fa.edu.ktxdmc_be.repository.UserRepository;
import fa.edu.ktxdmc_be.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Bill> getListBill() {
        return billRepository.findAll();
    }

    @Override
    public List<BillResponse> getListBillByPhoneNumber(String phoneNumber) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        List<Bill> billList = billRepository.findByUserID(user.getUserID());
        System.out.println("");
        return billList.stream()
                .map(bill -> mapper.map(bill, BillResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createBillForNewUser(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public Page<Bill> getListBillPageable(PageRequest pageRequest) {

        Page<Bill> billList = billRepository.findAll(pageRequest);
        return billList;
    }

    @Override
    public void updateBill(BillRequest billRequest) {
        Bill bill = billRepository.findById(billRequest.getBillId()).orElseThrow();
        bill.setStatus(billRequest.getStatus());
        billRepository.save(bill);
    }
}
