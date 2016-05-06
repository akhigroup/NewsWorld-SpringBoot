package pl.pwr.news.model.userstereotype;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.user.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jf on 5/6/16.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users_stereotypes")
@IdClass(UserStereotypeId.class)
public class UserStereotype implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "stereotype_id", referencedColumnName = "id")
    private Stereotype stereotype;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Long value = 0L;

    public UserStereotype(User user, Stereotype stereotype) {
        this.user = user;
        this.stereotype = stereotype;
    }

    public void incrementValue() {
        if (this.value == null) {
            this.value = 0L;
        }
        this.value++;
    }
}
