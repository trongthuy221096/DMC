package fa.edu.ktxdmc_be.controller;

import fa.edu.ktxdmc_be.common.Ultis;
import fa.edu.ktxdmc_be.dto.request.UsedServiceRequest;
import fa.edu.ktxdmc_be.dto.response.RoomResponse;
import fa.edu.ktxdmc_be.dto.response.UsedServiceResponse;
import fa.edu.ktxdmc_be.service.UsedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("usedService")
public class UsedServiceController {
    @Autowired
    private UsedServiceService usedServiceService;

    /**
     * This method is used to add a new used service based on the information provided in the request body.
     *
     * @param usedServiceRequest The request body containing the details of the used service to be created.
     * @return ResponseEntity representing the result of the creation operation.
     * - HttpStatus.OK if the used service is successfully created.
     */
    @PostMapping("create")
    public ResponseEntity<?> addUsedService(@RequestBody UsedServiceRequest usedServiceRequest) {
        try {
            usedServiceService.createUsedService(usedServiceRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("room")
    public ResponseEntity<?> getListRoomUsedService(@RequestParam(name = "thangChot", defaultValue = "") String thangChot) {
        try {
            LocalDate ngayChotDate = Ultis.stringToLocalDate(thangChot);

            List<RoomResponse> usedServiceResponseList = usedServiceService.getListRoomUsedServiceByEndDate(ngayChotDate);
            return ResponseEntity.ok().body(usedServiceResponseList);
        } catch (Exception ex) {
            return ResponseEntity.ok().body(ex);
        }
    }

    /**
     * Retrieves a list of used services based on the specified cutoff month.
     *
     * @param thangChot A string representing the cutoff month in the format "yyyy-MM" (e.g., "2023-09").
     * @return A ResponseEntity containing a list of RoomResponse objects representing used services.
     * @throws Exception If an error occurs during the retrieval process.
     */
    @GetMapping("")
    public ResponseEntity<?> getListUsedService(@RequestParam(name = "thangChot", defaultValue = "") String thangChot) {
        try {
            LocalDate ngayChotDate = Ultis.stringToLocalDate(thangChot);

            List<UsedServiceResponse> usedServiceResponseList = usedServiceService.getListUsedServiceByEndDate(ngayChotDate);
            return ResponseEntity.ok().body(usedServiceResponseList);
        } catch (Exception ex) {
            return ResponseEntity.ok().body(ex);
        }
    }
}
