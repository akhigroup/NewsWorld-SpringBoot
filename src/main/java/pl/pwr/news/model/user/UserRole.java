package pl.pwr.news.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evelan-E6540 on 06/09/2015.
 */

@Entity
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }
}
