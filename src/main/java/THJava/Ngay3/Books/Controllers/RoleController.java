package THJava.Ngay3.Books.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.Role;
import THJava.Ngay3.Books.Models.User;
import THJava.Ngay3.Books.Services.CartItemService;
import THJava.Ngay3.Books.Services.RoleService;
import THJava.Ngay3.Books.Services.UserService;

@Controller
@RequestMapping("/roles")
@ComponentScan("THJava.Ngay3.Books.Services")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String viewAllRole(Model model,Principal principal) {
		List<Role> listRoles = roleService.listAll();
		model.addAttribute("roles", listRoles);
		String username = principal.getName();
        User user = userService.findByUsername(username);
        List<CartItem> cartItems = cartItemService.getCartItems(user);
        model.addAttribute("count", cartItems.size());
		return "role/index";
	}
	
	@GetMapping("/new")
	public String showNewRolePage(Model model) {
		Role role = new Role();
		model.addAttribute("role", role);
		return "role/create";
	}
	
	@PostMapping("/save")
	public String saveRole(@ModelAttribute("role") Role role) {
		roleService.save(role);
		return "redirect:/roles";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditRolePage(@PathVariable("id") Long id, Model model) {
		Role role = roleService.get(id);
		
		if(role == null) {
			return "notfound";
		} else {
			model.addAttribute("role", role);
			return "role/edit";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable("id") Long id) {
		Role role = roleService.get(id);
		if(role == null) {
			return "notfound";
		} else {
			roleService.delete(id);
			return "redirect:/roles";
		}
	}
}
