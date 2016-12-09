package com.ddf.service;

import com.ddf.domain.User;
import com.ddf.repository.UserRepository;
import com.ddf.service.exception.UserEmailDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) throws UserEmailDuplicatedException {

        if (this.userRepository.findOneByEmail(user.getEmail()) != null) {
            throw new UserEmailDuplicatedException();
        }

        this.userRepository.save(user);
    }

    public User get(Long id) {
        return this.userRepository.findOne(id);
    }

    public User getByUserDetail(UserDetails userDetails) {
        return this.userRepository.findOneByEmail(userDetails.getUsername());
    }
}
