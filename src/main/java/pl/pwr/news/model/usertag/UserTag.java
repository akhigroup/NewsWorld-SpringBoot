package pl.pwr.news.model.usertag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.User;

import javax.persistence.*;

/**
 * Created by falfasin on 5/11/16.
 */

@Getter
@Setter
@Entity
@Table(name = "users_tags")
@IdClass(UserTagId.class)
@NoArgsConstructor
public class UserTag {

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    private Long tagValue = 0L;

    public UserTag(User user, Tag tag) {
        this.user = user;
        this.tag = tag;
    }

    public void incrementTagValue() {
        if (this.tagValue == null) {
            this.tagValue = 0L;
        }
        this.tagValue++;
    }
}
