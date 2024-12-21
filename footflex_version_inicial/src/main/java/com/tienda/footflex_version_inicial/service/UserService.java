
package com.tienda.footflex_version_inicial.service;

import com.tienda.footflex_version_inicial.domain.User;
import java.util.List;


public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User findUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    void deleteUser(Long id);
}
