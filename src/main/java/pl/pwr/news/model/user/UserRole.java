package pl.pwr.news.model.user;

import lombok.Getter;

/**
 * Created by Evelan-E6540 on 06/09/2015.
 */
public enum UserRole {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    @Getter
    String value;

    UserRole(String value) {
        this.value = value;
    }

}
