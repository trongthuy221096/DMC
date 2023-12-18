package fa.edu.ktxdmc_be.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fa.edu.ktxdmc_be.dto.response.UserResponse;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.repository.UserRepository;
import fa.edu.ktxdmc_be.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Page<UserResponse> findAllUser(PageRequest pageRequest) {
		return userRepository.findAllUser(pageRequest);
	}

	@Override
	public Page<UserResponse> findAllByCmnd(String seachValue, PageRequest pageRequest) {
		return userRepository.findAllByCmnd(seachValue, pageRequest);
	}

	@Override
	public UserResponse findById(Long id) {
		return userRepository.findUserById(id).orElseThrow(null);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByPhoneNumber(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber);
	}
}
