package pl.pwr.news.model.userstereotype;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by jf on 5/6/16.
 */
@Getter
@Setter
public class UserStereotypeId implements Serializable {

    private Long user;
    private Long stereotype;

    public int hashCode() {
        return (int)(user + stereotype);
    }

    public boolean equals(Object object) {
        if (object instanceof UserStereotypeId) {
            UserStereotypeId otherId = (UserStereotypeId) object;
            return (otherId.user.longValue() == this.user.longValue()) &&
                    (otherId.stereotype.longValue() == this.stereotype.longValue());
        }
        return false;
    }
}
