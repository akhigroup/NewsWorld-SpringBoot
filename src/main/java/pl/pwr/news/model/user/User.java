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
import java.util.*;

/**
 * Created by Evelan-E6540 on 29/08/2015.
 *
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User  implements Serializable {

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

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String token;

    @JsonIgnore
    private String activationHash;

    private Date birth;
    private Date registered;
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<UserRole> userRoles = new HashSet<>();

    private String hometown;

    //Te metody powinny być w serwisie raczej
    public boolean hasRoles(UserRole... roles) {
        for (UserRole role : roles) {
            if (!this.getUserRoles().contains(role)) {
                return false;
            }
        }
        return true;
    }

    public boolean addRole(UserRole... roles) {
        return this.userRoles.addAll(Arrays.asList(roles));
    }

    public boolean removeRole(UserRole... roles) {
        return this.userRoles.removeAll(Arrays.asList(roles));
    }



}

