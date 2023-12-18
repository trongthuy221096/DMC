package fa.edu.ktxdmc_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fa.edu.ktxdmc_be.repository.ServiceRepository;
import fa.edu.ktxdmc_be.service.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {
	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public Page<fa.edu.ktxdmc_be.model.Service> findAll(PageRequest pageRequest) {
		return serviceRepository.findAll(pageRequest);
	}

	@Override
	public boolean updateService(fa.edu.ktxdmc_be.model.Service serviceUpdate) {

		if (serviceUpdate.getServiceID() != null) {
			fa.edu.ktxdmc_be.model.Service service = serviceRepository.findById(serviceUpdate.getServiceID())
					.orElse(null);
			if (service != null) {
				serviceRepository.save(serviceUpdate);
				return true;
			}
		}

		return false;
	}

	@Override
	public Page<fa.edu.ktxdmc_be.model.Service> findByServiceName(String searchText, Pageable pageable) {
		return serviceRepository.findByServiceName(searchText, pageable);
	}

	@Override
	public Page<fa.edu.ktxdmc_be.model.Service> findByPrice(Double price, Pageable pageable) {
		return serviceRepository.findByPrice(price, pageable);
	}

}
