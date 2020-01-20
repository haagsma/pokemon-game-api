package com.poke.boot.services;

import com.poke.boot.repository.UserRepository;
import com.poke.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmailIgnoreCase(email)).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado!"));
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ADMIN", "USER");
        return new org.springframework.security.core.userdetails.User(user.getNick(), user.getPassword(), grantedAuthorities);
    }

}