package fa.edu.ktxdmc_be.controller;

import fa.edu.ktxdmc_be.common.EmailDetails;
import fa.edu.ktxdmc_be.common.UserDetailsImpl;
import fa.edu.ktxdmc_be.config.security.JwtAuthenticationProvider;
import fa.edu.ktxdmc_be.dto.request.LoginRequest;
import fa.edu.ktxdmc_be.dto.request.RegisterRequest;
import fa.edu.ktxdmc_be.dto.request.RequestCreateRequest;
import fa.edu.ktxdmc_be.dto.response.MessageResponse;
import fa.edu.ktxdmc_be.dto.response.UserInfoResponse;
import fa.edu.ktxdmc_be.model.User;
import fa.edu.ktxdmc_be.service.CustomUserDetailsService;
import fa.edu.ktxdmc_be.service.EmailService;
import fa.edu.ktxdmc_be.service.RequestService;
import fa.edu.ktxdmc_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * Represents an AuthController
 *
 * @author
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired
	private RequestService requestService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;

	/**
	 * Process a user registration request and create a new user if successful.
	 *
	 * @param registerRequest The RegisterRequest object containing user
	 *                        registration information.
	 * @return ResponseEntity containing a message response: - If registration is
	 *         successful, it returns "successful". - If the user already exists, it
	 *         returns "existed". - If an error occurs during registration, it
	 *         returns "failed".
	 */
	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		if (customUserDetailsService.saveUser(registerRequest) == 1) {
			long userID = customUserDetailsService.getUserIdByCmnd(registerRequest.getCmnd());
			RequestCreateRequest request = new RequestCreateRequest(userID, "create", "waiting");
			requestService.saveRequest(request);
			return ResponseEntity.ok().body(new MessageResponse("successful"));
		} else if (customUserDetailsService.saveUser(registerRequest) == 0) {
			return ResponseEntity.ok().body(new MessageResponse("existed"));
		} else {
			return ResponseEntity.ok().body(new MessageResponse("failed"));
		}
	}

	/**
	 * Process a user login request and issue a JWT token if authentication is
	 * successful.
	 *
	 * @param loginRequest The LoginRequest object containing user login
	 *                     information.
	 * @return ResponseEntity containing user information and a JWT token if login
	 *         is successful, or a "Login failed" message if login fails.
	 */
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			String jwtCookie = jwtAuthenticationProvider.generateToken(userDetails);
			User user = customUserDetailsService.getUserByPhoneNumber(loginRequest.getPhoneNumber());

			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie)
					.body(new UserInfoResponse(user, jwtCookie));
		} catch (Exception ex) {
			return ResponseEntity.ok().body(new MessageResponse("Login failed"));
		}
	}

	/**
	 * This method is responsible for sending a reset password email to a user based
	 * on their provided email address.
	 *
	 * @param email The email address of the user requesting a password reset.
	 * @return ResponseEntity representing the result of the password reset request.
	 *         - HttpStatus.OK if the email was sent successfully. -
	 *         HttpStatus.NOT_FOUND if no user with the provided email was found. -
	 *         HttpStatus.INTERNAL_SERVER_ERROR if an unexpected error occurred
	 *         during the process.
	 * @author ThuyTT77
	 */
	
	@GetMapping("/forgotpassword")
	public ResponseEntity<?> sendEmailResetPassword(@RequestParam(name = "email", defaultValue = "") String email) {
		try {
			User user = userService.findByEmail(email);
			if (user != null) {
				String newPassword = customUserDetailsService.initPasswordUser(user.getUserID());
				EmailDetails emailDetails = new EmailDetails();
				emailDetails.setRecipient(user.getEmail());
				emailDetails.setSubject("Thông báo tài khoản và mật khẩu");
				emailDetails.setMsgBody("Username: Số điện thoại đăng ký\n" + "Password:" + newPassword);
				emailService.sendSimpleMail(emailDetails);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
