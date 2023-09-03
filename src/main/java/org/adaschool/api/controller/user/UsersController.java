package org.adaschool.api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Users", description = "Users management APIs")
@RestController
@RequestMapping("/v1/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User userToSave = new User(userDto);
        User userSaved = usersService.save(userToSave);
        URI createdUserUri = URI.create(userSaved.toString());
        return ResponseEntity.created(createdUserUri).body(null);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = usersService.all();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Retrieve a User by Id",
            description = "Get a User object by specifying its id.",
            tags = "users, get")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        Optional<User> user = usersService.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.ok(user.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable("id") String id) {
        Optional<User> userInDb = usersService.findById(id);
        if (userInDb.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        userInDb.get().update(userDto);
        usersService.save(userInDb.get());
        return ResponseEntity.ok(userInDb.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        Optional<User> userInDb = usersService.findById(id);
        if (userInDb.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        usersService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
