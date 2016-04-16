package pl.pwr.news.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Evelan-E6540 on 29/08/2015.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails,Serializable {

    private static final long serialVersionUID = 2427238057150579366L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String username;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    @Email
    private String email;

    private String password;

    @Column(unique = true)
    private String token;

    @Column(columnDefinition = "TEXT")
    private String deviceToken;

    @JsonIgnore
    private String activationHash;

    private Date birth;
    private Date registered;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String hometown;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        return authorities;
    }
    @Override
    public String getUsername() {
        return username;
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
        return enabled;
    }

}

