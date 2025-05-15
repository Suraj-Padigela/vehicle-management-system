package com.app.vms.Service;

import com.app.vms.entity.User;
import com.app.vms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getFirstName() != null)
                existingUser.setFirstName(updatedUser.getFirstName());

            if (updatedUser.getLastName() != null)
                existingUser.setLastName(updatedUser.getLastName());

            if (updatedUser.getEmail() != null)
                existingUser.setEmail(updatedUser.getEmail());

            if (updatedUser.getPhone() != null)
                existingUser.setPhone(updatedUser.getPhone());

            if (updatedUser.getAddress() != null)
                existingUser.setAddress(updatedUser.getAddress());

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }


}
