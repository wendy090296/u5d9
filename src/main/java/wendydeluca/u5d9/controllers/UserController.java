package wendydeluca.u5d9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wendydeluca.u5d9.entities.Device;
import wendydeluca.u5d9.entities.User;
import wendydeluca.u5d9.exceptions.BadRequestException;
import wendydeluca.u5d9.payloads.UserDTO;
import wendydeluca.u5d9.services.UserService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy){
        return userService.getAllUsers(page,size,sortBy);
    }

    @PostMapping //SAVE
    @ResponseStatus(HttpStatus.CREATED) //STATUS 201 OK
    public User saveUser(@RequestBody @Validated UserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return userService.saveUser(body);
    }

    @GetMapping("/{userId}") //GET BY ID
    public User findUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);

    }

    @PutMapping("/{userId}") // UPDATING 1
    public User findUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody @Validated UserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        }
        return userService.updateUser(userId,body);
    }



    @DeleteMapping("/{userId}") // DELETE by id
    public void deleteUser(@PathVariable UUID userId){
        userService.deleteUser(userId);

    }



    @PostMapping("/upload")
    public String uploadImage(@RequestParam("profilePicture") MultipartFile image) throws IOException {
        // "profilePicture" deve essere esattamente uguale alla chiave del Multipart dove sarà conte il file

        return  this.userService.uploadImage(image);


    }



}

