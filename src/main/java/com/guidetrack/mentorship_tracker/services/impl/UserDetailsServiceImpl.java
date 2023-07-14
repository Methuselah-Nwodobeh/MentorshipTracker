package com.guidetrack.mentorship_tracker.services.impl;

import com.guidetrack.mentorship_tracker.models.basemodels.BaseUserModel;
import com.guidetrack.mentorship_tracker.models.model_to_details.BaseUserDetails;
import com.guidetrack.mentorship_tracker.repositories.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final BaseUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BaseUserModel> userInfo = userRepository.findByEmail(username);
        return userInfo.map(BaseUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
