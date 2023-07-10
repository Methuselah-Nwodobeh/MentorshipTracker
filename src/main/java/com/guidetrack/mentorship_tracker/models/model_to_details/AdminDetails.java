package com.guidetrack.mentorship_tracker.models.model_to_details;

import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AdminDetails implements UserDetails {
    private transient RoleRepository roleRepository;
    private  String username;
    private  String password;
    private  transient Admin admin;

    public AdminDetails(Admin admin, RoleRepository roleRepository){
        this.username = admin.getEmail();
        this.password = admin.getPassword();
        this.admin = admin;
        this.roleRepository = roleRepository;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Optional<Role> role = roleRepository.findByNameIgnoreCase("Administrator");
        return role.map(value -> List.of(new SimpleGrantedAuthority(value.toString()))).orElse(null);
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
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return admin.isVerified();
    }
}
