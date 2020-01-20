package com.poke.boot.services;

import com.poke.boot.model.Status;
import com.poke.boot.model.User;
import com.poke.boot.repository.StatusRepository;
import com.poke.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    public void create(User user) {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Status status = statusRepository.findByNemotecnicoIgnoreCaseContaining("ATIVO");
        user.setStatus(status);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(Integer id) {

    }
}
