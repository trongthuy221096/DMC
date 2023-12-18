package fa.edu.ktxdmc_be.controller;

import fa.edu.ktxdmc_be.common.UserDetailsImpl;
import fa.edu.ktxdmc_be.dto.request.ChangePassWordRequest;
import fa.edu.ktxdmc_be.dto.response.UserResponse;
import fa.edu.ktxdmc_be.service.CustomUserDetailsService;
import fa.edu.ktxdmc_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Represents an UserController
 *
 * @author
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieve a paginated list of users or perform searches based on various
     * criteria.
     *
     * @param page        The page number for the result (default is 1 if not
     *                    provided).
     * @param pageSize    The number of users per page in the result (default is 5
     *                    if not provided).
     * @param valueSearch The value to search for users (default is an empty string
     *                    if not provided).
     * @return ResponseEntity containing a paginated list of UserResponse objects if
     * successful, or a "NO_CONTENT" response status if there are no users,
     * or an "INTERNAL_SERVER_ERROR" response status if an error occurs
     * during processing.
     */
    @GetMapping("")
    public ResponseEntity<Page<UserResponse>> getAllUser(@RequestParam(name = "page", defaultValue = "1") int page,
                                                         @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                         @RequestParam(name = "valueSearch", defaultValue = "") String valueSearch) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<UserResponse> servicePage;

        if ("".equals(valueSearch)) {
            servicePage = userService.findAllUser(pageRequest);
        } else {
            servicePage = userService.findAllByCmnd(valueSearch, pageRequest);
        }

        if (servicePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Page<UserResponse> userResponsePage = servicePage.map(UserResponse::new);

        return new ResponseEntity<>(userResponsePage, HttpStatus.OK);
    }

    /**
     * Retrieve details of a user by their unique identifier.
     *
     * @param id The unique identifier of the user for which to fetch details.
     * @return ResponseEntity containing a UserResponse object with user details if
     * successful, or an "INTERNAL_SERVER_ERROR" response status if an error
     * occurs during retrieval.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long id,
                                           Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            UserResponse userResponse = userService.findById(id);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } else if (userDetails.getUserId() == id) {
            UserResponse userResponse = userService.findById(id);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } else {
            // Nếu không khớp, trả về lỗi hoặc thông báo "Không có quyền truy cập"
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Performs a password change operation for a user based on the provided request.
     *
     * @param changePasswordRequest The request to change the user's password.
     * @param result                The result of validation and any related request errors.
     * @return ResponseEntity with HTTP status code and corresponding response message:
     * - HttpStatus.OK (200): If the password change operation is successful.
     * - HttpStatus.NOT_FOUND (404): If no user account is found to change the password.
     * - HttpStatus.INTERNAL_SERVER_ERROR (500): If an internal error occurs during the password change.
     * - HttpStatus.BAD_REQUEST (400): If the change password request is invalid or has validation errors.
     */
    @PutMapping("/updatePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassWordRequest changePasswordRequest,
                                            BindingResult result) {
        if (!result.hasErrors()) {
            try {
                if (customUserDetailsService.changePassword(changePasswordRequest)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return ResponseEntity.badRequest().body("Invalid Data");

    }

}
