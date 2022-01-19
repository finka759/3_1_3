package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.enteties.Role;
import ru.kata.spring.boot_security.demo.enteties.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserServiceImp userServiceImp;

    @Autowired
    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/admin")
    public String findAll(Model model){
        List<User> users = userServiceImp.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/admin/user_create")
    public String createUserForm(User user, Model model){
        List<Role> listRoles = userServiceImp.listRoles();
        model.addAttribute("listRoles", listRoles);
        return "user_create";
    }


    @PostMapping("/admin/user_create")
    public String createUser(User user){
        userServiceImp.saveUser(user);
        return "redirect:/admin ";
    }

    @GetMapping ("/admin/user_delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userServiceImp.deleteUser(id);
        return "redirect:/admin ";
    }

    @GetMapping("/admin/user_update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userServiceImp.getUser(id);
        user.setPassword("");
        List<Role> listRoles = userServiceImp.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);

        return "user_update";
    }
    @PostMapping("/admin/user_update")
    public String updateUser(User user){
        userServiceImp.saveUser(user);
        return "redirect:/admin ";
    }

    @GetMapping("/user")
    public String showUser(Model model, Principal principal){
        User user = userServiceImp.findByUsername(principal.getName());
        user.setPassword("");
        model.addAttribute("user", user);
        return "user";
    }

}
