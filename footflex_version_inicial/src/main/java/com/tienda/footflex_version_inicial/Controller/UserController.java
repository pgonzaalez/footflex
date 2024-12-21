/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.footflex_version_inicial.Controller;

import com.tienda.footflex_version_inicial.dao.UserDAO;
import com.tienda.footflex_version_inicial.domain.User;
import com.tienda.footflex_version_inicial.service.UserService;
import com.tienda.footflex_version_inicial.service.CsvGenerator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CsvGenerator csvGenerator;

    @Autowired
    private UserDAO UserDAO;

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        try {
            user.setRole("USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            // Captura la excepción de nombre de usuario ya existente y muestra un mensaje de error
            model.addAttribute("error", "El nombre de usuario ya está en uso. Por favor, elige otro.");
            return "/admin/register";
        }
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "/admin/crud_users";
    }
    // csv generator
    

    @GetMapping("/users/csv")
    public ResponseEntity<byte[]> generateCsvFile() {
        List<User> user = UserDAO.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "users.csv");

        byte[] csvBytes = csvGenerator.generateUsersCsv(user).getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/createUser")
    public String showCreate(User user, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "/admin/create_user";
    }

    @GetMapping("/updateUser/{id}")
    public String update(User user, Model model) {
        user = userService.findUser(user);
        model.addAttribute("user", user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "/admin/edit_user";
    }

    @PostMapping("/users")
    public String submitForm(User user, Model model) {
        try {
            // Save the person object to the database
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return "redirect:/users";
        } catch (DataIntegrityViolationException e) {
            // Captura la excepción de nombre de usuario ya existente y muestra un mensaje de error
            model.addAttribute("error", "El nombre de usuario ya está en uso. Por favor, elige otro.");
            return "/admin/create_user";
        }
    }

    @GetMapping("/deleteUser/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "/admin/user-delete-confirmation";
    }

    @GetMapping("/deleteUserOK/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
