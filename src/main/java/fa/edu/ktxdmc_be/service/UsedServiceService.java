package fa.edu.ktxdmc_be.service;

import fa.edu.ktxdmc_be.dto.request.UsedServiceRequest;
import fa.edu.ktxdmc_be.dto.response.RoomResponse;
import fa.edu.ktxdmc_be.dto.response.UsedServiceResponse;

import java.time.LocalDate;
import java.util.List;

public interface UsedServiceService {
    /**
     * Creates a new used service based on the information provided in the UsedServiceRequest.
     *
     * @param usedServiceRequest The request object containing the details of the used service to be created.
     */
    void createUsedService(UsedServiceRequest usedServiceRequest);

    /**
     * Retrieves a list of RoomResponse objects representing used services by their end date.
     *
     * @param endDate The end date for filtering used services.
     * @return A list of RoomResponse objects representing used services that have an end date
     * matching or earlier than the provided end date.
     */
    List<UsedServiceResponse> getListUsedServiceByEndDate(LocalDate endDate);

    List<RoomResponse> getListRoomUsedServiceByEndDate(LocalDate endDate);

}
