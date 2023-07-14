package com.guidetrack.mentorship_tracker.models.model_to_details;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class BaseUserDetails implements UserDetails {
    private  String username;
    private  String password;
    private transient BaseUserModel baseUserModel;
    private String role;

    public BaseUserDetails(BaseUserModel baseUserModel){
        this.username = baseUserModel.getEmail();
        this.password = baseUserModel.getPassword();
        this.baseUserModel = baseUserModel;
        this.role = baseUserModel.getRole().getName();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("this is granted authorities {}", List.of(new SimpleGrantedAuthority(role.toUpperCase())));
         return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return baseUserModel.isVerified();
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
}
