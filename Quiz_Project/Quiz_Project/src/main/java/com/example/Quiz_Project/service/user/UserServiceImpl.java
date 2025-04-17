package com.example.Quiz_Project.service.user;
import com.example.Quiz_Project.entities.User;
import com.example.Quiz_Project.enums.UserRole;
import com.example.Quiz_Project.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    //Admin
    @PostConstruct
    private void createAdminUser() {
        User optionalUser = userRepository.findByRole(UserRole.ADMIN);
        if (optionalUser == null){
            User user= new User();
            user.setName("Admin");
            user.setEmail("admin@gmail.com");
            user.setRole(UserRole.ADMIN);
            user.setPassword("admin");

            userRepository.save(user);
        }
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    // create user
    public User createUser(User user){
        if (user.getRole() == null) {
            user.setRole(UserRole.USER); // fallback default
        }
        return userRepository.save(user);
    }

    public User login(User user){
        Optional<User> optionalUser=userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent() && user.getPassword().equals(optionalUser.get().getPassword())){
            return optionalUser.get();
        }
        return null;
    }

}
