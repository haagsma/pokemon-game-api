package com.poke.boot.controller;

import com.poke.boot.repository.GymRepository;
import com.poke.boot.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/gym")
@RestController
public class GymController {

    @Autowired
    private GymRepository gymRepository;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
       return new ResponseEntity<>(gymRepository.findAll(), HttpStatus.OK);
   }
}
