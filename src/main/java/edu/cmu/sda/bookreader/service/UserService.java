package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
@Scope(value = "session")
@Component(value = "userService")
public class UserService {

    @Autowired
    private UserRepository repository;

    private EntityManager entityManager;

    /**
     * save a new user to db
     * @param user user to be saved
     * @return the saved user with id
     */
    public User saveUser(User user) {
        return repository.save(user);
    }

    /**
     * find user by id or return null if user is not found
     * @param id user id
     * @return user with input id
     */
    public User getUser(long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * find all users
     * @return list of all users
     */
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /**
     * delete given user
     * @param user user to be removed
     */
    public void removeUser(User user) {
        repository.delete(user);
    }

    /**
     * find user by name, return null if no user with name is found
     * @param name user name
     * @return user with input name
     */
    public User getUserByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    /**
     * authenticate if given user
     * @param user user to be authenticated
     * @return user id if authenticated, else null
     */
    public Long authenticateUser(User user) {
        User currentUser = repository.findByName((user.getName())).orElse(null);
        if (null != currentUser && user.getPassword().equals(currentUser.getPassword())) {
            return currentUser.getId();
        } else {
            return null;
        }
    }



}
