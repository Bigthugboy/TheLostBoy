package codewiththugboy.customer.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer implements UserDetails {
    @jakarta.persistence.Id
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    @Column(unique = true)
    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})")
    private String email;

    private String PhoneNumber;
    @Size(min = 8)
    private String Password;
    private RoleType roles;

    private Boolean locked = false;

    private Boolean enabled = true;

    private Boolean isSuspended = false;

    private LocalDateTime date = LocalDateTime.now();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.toString()));
    }



    @Override
    public String getUsername() {
        return email;
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
}
