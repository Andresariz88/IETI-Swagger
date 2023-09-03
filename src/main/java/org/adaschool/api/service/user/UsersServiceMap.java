package org.adaschool.api.service.user;

import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceMap implements UsersService {

    @Override
    public User save(User user) {
        return new User("1", user.getName(), user.getLastName(), user.getEmail(), "");
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(new User("1", "andres", "ariza", "andres@gmail.com", ""));
    }

    @Override
    public List<User> all() {
        return List.of(new User("1", "andres", "ariza", "andres@gmail.com", ""));
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public User update(User user, String userId) {
        return null;
    }
}
