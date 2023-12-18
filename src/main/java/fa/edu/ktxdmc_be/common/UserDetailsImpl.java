package fa.edu.ktxdmc_be.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fa.edu.ktxdmc_be.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private long userId;
    private long roomId;
    private String phoneNumber;
    private String name;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        String role = user.getRole().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
        updatedAuthorities.add(authority);
        return new UserDetailsImpl(user.getUserID(), user.getRoom().getRoomID(), user.getPhoneNumber(), user.getName(), user.getPassword(), updatedAuthorities);
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoomId() {
        return roomId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl userDetails = (UserDetailsImpl) obj;
        return Objects.equals(userId, userDetails.userId);
    }
}
