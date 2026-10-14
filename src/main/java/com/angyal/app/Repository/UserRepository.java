// package com.angyal.app.Repository;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// public class UserRepository {

//     @Autowired
//     private BCryptPasswordEncoder bCryptPasswordEncoder;

//     private List<User> users = new ArrayList<>();

//     public UserRepository() {

        
        
        
//     }

    
    
//     @PostConstruct
//     public void init() {
//         public UserDAO user1 = new UserDAO("user1", "password1");

//         String encodedPassword = bCryptPasswordEncoder.encode(user1.getPassword());

//         User userEntity = new User();
//         userEntity.setId(1);
//         userEntity.setName(user1.getUsername());
//         userEntity.setPasswordBcrypted(encodedPassword);

//         users.add(userEntity);
//     }
    
    
// }


package com.angyal.app.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.angyal.app.Models.User;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private int currentId = 1;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepository() {
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        
        String encodedPassword1 = new BCryptPasswordEncoder().encode("password1");
        User user1 = new User(currentId++, "user1", encodedPassword1);
        users.add(user1);

        String encodedPassword2 = new BCryptPasswordEncoder().encode("password2");
        User user2 = new User(currentId++, "user2", encodedPassword2);
        users.add(user2);
    }


    public User findByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public Optional<User> findByNameOptional(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    public boolean existsByName(String name) {
        return users.stream()
                .anyMatch(user -> user.getName().equals(name));
    }

    public Optional<User> authenticate(String username, String rawPassword) {
        Optional<User> user = findByNameOptional(username);
        
        if (user.isPresent()) {
            // Porovnanie surového hesla s uloženým bcrypt heslom
            if (bCryptPasswordEncoder.matches(rawPassword, user.get().getPassword())) {
                return user;
            }
        }
        return Optional.empty();
    }

    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(currentId++);
        }
        users.add(user);
        return user;
    }

    public User register(String username, String rawPassword) {
        if (existsByName(username)) {
            throw new IllegalArgumentException("Užívateľ s týmto menom už existuje!");
        }
        
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        User newUser = new User(currentId++, username, encodedPassword);
        users.add(newUser);
        return newUser;
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }  

    public long count() {
        return users.size();
    }
}
