package com.calculate.userauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public class UserAuthException extends Exception {
        public UserAuthException(String message) {
            super(message);
        }
    }

    @Autowired
    private UserRepository userAuthRepo;

    public User registerUser(String username, String password) throws UserAuthException {
        if (username != null && password != null && username != "" && password != "") {
            if (userAuthRepo.findUserByUsername(username) != null)
                throw new UserAuthException("ALREADY_EXIST");
            User user = new User(username, password);
            return userAuthRepo.save(user);
        } else {
            throw new UserAuthException("NULL_USERNAME_OR_PASSWORD");
        }
    }

    public User loginUser(String username, String password) throws UserAuthException {
        if (username != null && password != null && username != "" && password != "") {
            if (userAuthRepo.findUserByUsername(username) != null) {
                if (userAuthRepo.findUserByUsernameAndPassword(username, password) != null) {
                    return new User(username, password);
                } else
                    throw new UserAuthException("INVALID_PASSWORD");
            } else
                    throw new UserAuthException("NOT_EXIST");

        } else {
            throw new UserAuthException("NULL_USERNAME_OR_PASSWORD");
        }
    }
}
