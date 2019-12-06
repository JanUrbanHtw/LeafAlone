package group11.leafalone.Auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LeafAloneUserPrincipal implements UserDetails {
    private LeafAloneUser leafAloneUser;

    public LeafAloneUserPrincipal(LeafAloneUser leafAloneUser) {
        this.leafAloneUser = leafAloneUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return leafAloneUser.getPassword();
    }

    public Long getId() {
        return leafAloneUser.getId();
    }

    public LeafAloneUser getUser() {
        return leafAloneUser;
    }

    @Override
    public String getUsername() {
        return leafAloneUser.getUsername();
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
        return true;
    }

}
