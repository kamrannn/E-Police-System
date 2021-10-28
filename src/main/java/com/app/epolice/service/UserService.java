package com.app.epolice.service;

import com.app.epolice.model.entity.User;
import com.app.epolice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    /**
     * Initializing the user Repository
     */
    final UserRepository userRepository;

    /**
     * Parameterized constructor
     *
     * @param userRepository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is fetching all the users from the database
     *
     * @return
     */
    public ResponseEntity<Object> ListAllUsers() {
        try {
            List<User> userList = userRepository.findAllByStatus(true);
            if (userList.isEmpty()) {
                return new ResponseEntity<>("There are no users in the database", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(userList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method is adding the user
     *
     * @param user
     * @return
     */
    public ResponseEntity<Object> AddUser(User user) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
            if (existingUser.get().isStatus() == false) {
                existingUser.get().setStatus(true);
                userRepository.save(existingUser.get());
                return new ResponseEntity<>("User is successfully added", HttpStatus.OK);
            } else if (existingUser.get().isStatus()) {
                return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
            } else {
                user.setCreatedDate(date);
                user.setStatus(true);
                userRepository.save(user);
                return new ResponseEntity<>("User is successfully added", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This serivce method is updating the user
     *
     * @param user
     * @return
     */
    public ResponseEntity<Object> updateUser(User user) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            user.setUpdatedDate(date);
            userRepository.save(user);
            return new ResponseEntity<>("User has been successfully Updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User is not Updated", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete user from db by using user ID
     *
     * @param id
     * @return
     */
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return new ResponseEntity<>("There is no user against this id", HttpStatus.NOT_FOUND);
            } else {
                user.get().setUpdatedDate(date);
                user.get().setStatus(false);
                userRepository.save(user.get());
                return new ResponseEntity<>("User is successfully deleted", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("This user doesn't exist in the database", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete multiple users from db by using multiple users object
     *
     * @param userList
     * @return
     */
    public ResponseEntity<Object> DeleteMultipleUsers(List<User> userList) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String date = formatter.format(new Date());
            try{
                if(userList.isEmpty()){
                    return new ResponseEntity<>("The entered list is empty", HttpStatus.BAD_REQUEST);
                }else {
                    for (User user : userList
                    ) {
                        Optional<User> existingUser = userRepository.findById(user.getId());
                        if (existingUser.isEmpty()) {
                            return new ResponseEntity<>("There is no user against this id: " + user.getId(), HttpStatus.NOT_FOUND);
                        } else {
                            existingUser.get().setUpdatedDate(date);
                            existingUser.get().setStatus(false);
                            userRepository.save(existingUser.get());
                            return new ResponseEntity<>("Users are successfully deleted", HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<>("Successfully added",HttpStatus.OK);
            }catch (Exception exception){
                return null;
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
