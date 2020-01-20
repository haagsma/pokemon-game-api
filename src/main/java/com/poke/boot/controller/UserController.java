package com.poke.boot.controller;

import com.poke.boot.model.User;
import com.poke.boot.repository.UserRepository;
import com.poke.boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Map<String, String> userInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("Nome", "Jhonatan");
        return info;
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAll() {
        return userRepository.findAll();
    }


    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody User user){
        System.out.println(user);
        userService.create(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
