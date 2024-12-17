package com.code.journalApp.Controller;


import com.code.journalApp.entity.User;
import com.code.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

     @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
  User userINDb = userService.findByUserName(user.getUserName());
  if(userINDb != null){
      userINDb.setUserName(user.getUserName());
      userINDb.setPassword(user.getPassword());
      userService.saveEntry(userINDb);
  }
  return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }



}
