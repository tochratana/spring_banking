package com.tochratana.mb_api.security;

import com.tochratana.mb_api.domain.User;
import com.tochratana.mb_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username " +
                "not found"));

        CustomerUserDetail customerUserDetail = new CustomerUserDetail();
        customerUserDetail.setUser(user);
        return customerUserDetail;
    }
}
