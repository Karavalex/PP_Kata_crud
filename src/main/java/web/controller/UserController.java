package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


@Controller
@RequestMapping()
@EnableTransactionManagement
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String users(Model model) {
        model.addAttribute("getAllUsers" , userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user" , new User());
        return "new";
    }

    @GetMapping("/delete/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/updateUser")
    public String updateUser(@ModelAttribute("user") User user , @PathVariable("id") int id) {
        userService.updateUser(id , user);
        return "redirect:/";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model , @PathVariable("id") int id) {
        model.addAttribute("user" , userService.getUserById(id));
        return "/edit";
    }

    @GetMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/";
    }


}
