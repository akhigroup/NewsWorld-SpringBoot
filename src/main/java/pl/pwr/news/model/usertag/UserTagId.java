package pl.pwr.news.model.usertag;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by falfasin on 5/11/16.
 */

@Getter
@Setter
public class UserTagId implements Serializable {

    private Long user;
    private Long tag;

    public int hashCode() {
        return (int)(user + tag);
    }

    public boolean equals(Object object) {
        if (object instanceof UserTagId) {
            UserTagId otherId = (UserTagId) object;
            return (otherId.user.longValue() == this.user.longValue()) &&
                    (otherId.tag.longValue() == this.tag.longValue());
        }
        return false;
    }
}
