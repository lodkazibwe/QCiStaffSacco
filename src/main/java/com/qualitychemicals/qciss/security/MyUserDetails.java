package com.qualitychemicals.qciss.security;

import com.qualitychemicals.qciss.profile.model.Profile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class MyUserDetails  implements UserDetails {
    private Profile profile;
    public MyUserDetails(Profile profile){
        this.profile = profile;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profile.getRole().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.getRole()))
                .collect(Collectors.toList());
    }
        public int getId() {
        return profile.getId();
    }
    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getUserName();
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
