
package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.dao.UserDAO;
import com.tienda.footflex_version_inicial.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User saveUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User findUser(User user) {
        return userDAO.findById(user.getId()).orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userDAO.findById(id).orElse(null);
        userDAO.delete(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findByName(username);
    }

}
