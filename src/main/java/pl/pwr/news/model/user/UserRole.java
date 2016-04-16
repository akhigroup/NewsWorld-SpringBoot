package pl.pwr.news.model.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Evelan-E6540 on 06/09/2015.
 */

public enum  UserRole implements GrantedAuthority{

    ADMIN,USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
