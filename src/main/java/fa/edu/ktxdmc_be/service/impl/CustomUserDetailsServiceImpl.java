package fa.edu.ktxdmc_be.service.impl;

import fa.edu.ktxdmc_be.common.UserDetailsImpl;
import fa.edu.ktxdmc_be.dto.request.ChangePassWordRequest;
import fa.edu.ktxdmc_be.dto.request.RegisterRequest;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.repository.RoomRepository;
import fa.edu.ktxdmc_be.repository.UserRepository;
import fa.edu.ktxdmc_be.service.CustomUserDetailsService;
import fa.edu.ktxdmc_be.util.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    public static String generatePassword() {
        String pattern = "#########";
        SecureRandom random = new SecureRandom();
        StringBuilder generatedPassword = new StringBuilder(pattern.length());

        for (char c : pattern.toCharArray()) {
            if (c == '#') {
                // Nếu ký tự là #, thêm một ký tự ngẫu nhiên vào mật khẩu
                char randomChar = getRandomCharacter();
                generatedPassword.append(randomChar);
            } else {
                // Nếu ký tự không phải #, thêm ký tự đó vào mật khẩu
                generatedPassword.append(c);
            }
        }

        return generatedPassword.toString();
    }

    public static char getRandomCharacter() {
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?";

        String allChars = lowerCaseChars + upperCaseChars + digitChars + specialChars;

        int randomIndex = new SecureRandom().nextInt(allChars.length());
        return allChars.charAt(randomIndex);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetailsImpl.build(user);
    }

    @Override
    public User getUserByUserId(long userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        return user.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public long getUserIdByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user.getUserID();
    }

    @Override
    public long getUserIdByCmnd(String cmnd) throws UsernameNotFoundException {
        User user = userRepository.findUserByCmnd(cmnd);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user.getUserID();
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    @Override
    public int saveUser(RegisterRequest registerRequest) {
        try {
            User userCheckCmnd = userRepository.findUserByCmnd(registerRequest.getCmnd());
            User userCheckSdt = userRepository.findUserByPhoneNumber(registerRequest.getPhoneNumber());
            User userCheckEmail = userRepository.findByEmail(registerRequest.getEmail());
            if (userCheckCmnd == null && userCheckSdt == null && userCheckEmail == null) {
                User newUser = new User();
                newUser.setName(registerRequest.getName());
                newUser.setGender(registerRequest.getGender());
                newUser.setBirthDay(registerRequest.getBirthDay());
                String[] addressArray = {registerRequest.getCity(), registerRequest.getDistrict(),
                        registerRequest.getWard(), registerRequest.getStreet()};
                String addressUser = Arrays.stream(addressArray).collect(Collectors.joining("; "));
                newUser.setAddress(addressUser);
                newUser.setEmail(registerRequest.getEmail());
                newUser.setCmnd(registerRequest.getCmnd());
                newUser.setPhoneNumber(registerRequest.getPhoneNumber());
                newUser.setActive(false);
                newUser.setRoom(roomRepository.findByRoomID(registerRequest.getRoomID()));
                newUser.setRole(RoleEnum.USER.toString());

                userRepository.save(newUser);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    @Override
    public String initPasswordUser(long userID) {
        User user = userRepository.findUserByUserID(userID);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String randomPassword = generatePassword();
        String encodedPassword = passwordEncoder.encode(randomPassword);
        user.setPassword(encodedPassword);
        user.setActive(true);

        userRepository.save(user);
        return randomPassword;
    }

    @Override
    public boolean changePassword(ChangePassWordRequest changePassWordRequest) {
        User user = userRepository.findUserByPhoneNumber(changePassWordRequest.getPhoneNumber());
        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(changePassWordRequest.getCurrentPassword(), user.getPassword())) {
                String newPassword = passwordEncoder.encode(changePassWordRequest.getPassword());
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }

        }
        return false;
    }
}
