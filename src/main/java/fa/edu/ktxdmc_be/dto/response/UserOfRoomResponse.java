package fa.edu.ktxdmc_be.dto.response;

import fa.edu.ktxdmc_be.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class UserOfRoomResponse {
    private Long userID;

    private String name;

    private String gender;

    private String email;

    private String phoneNumber;

    public UserOfRoomResponse(User user) {
        if (user != null) {
            BeanUtils.copyProperties(user, this);
        }
    }
}
