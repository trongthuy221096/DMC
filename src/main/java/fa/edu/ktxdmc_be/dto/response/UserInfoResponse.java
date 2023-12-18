package fa.edu.ktxdmc_be.dto.response;

import fa.edu.ktxdmc_be.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private long userId;
    private String phoneNumber;
    private String accessToken;
    private String tokenType = "Bearer";

    public UserInfoResponse(User user, String accessToken) {
        this.userId = user.getUserID();
        this.phoneNumber = user.getPhoneNumber();
        this.accessToken = accessToken;
    }
}