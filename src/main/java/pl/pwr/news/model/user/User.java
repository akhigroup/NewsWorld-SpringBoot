package pl.pwr.news.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evelan-E6540 on 29/08/2015.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements Serializable {

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

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}))
    @Column(name = "role", columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Set<UserRole> userRole = new HashSet<>();

    private String hometown;

    public User() {

    }

    public boolean hasRoles(UserRole... roles) {
        for (UserRole role : roles) {
            if (!this.getUserRole().contains(role)) {
                return false;
            }
        }
        return true;
    }

    public boolean addRole(UserRole... roles) {
        return this.userRole.addAll(Arrays.asList(roles));
    }

    public boolean removeRole(UserRole... roles) {
        return this.userRole.removeAll(Arrays.asList(roles));
    }

    public boolean isAdmin() {
        return hasRoles(UserRole.ROLE_ADMIN);
    }
}

