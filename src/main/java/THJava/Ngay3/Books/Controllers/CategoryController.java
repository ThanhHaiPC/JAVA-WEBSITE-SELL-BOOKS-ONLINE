package THJava.Ngay3.Books.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.Category;
import THJava.Ngay3.Books.Models.User;
import THJava.Ngay3.Books.Services.BookServices;
import THJava.Ngay3.Books.Services.CartItemService;
import THJava.Ngay3.Books.Services.CategoryService;
import THJava.Ngay3.Books.Services.UserService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryservice;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private UserService userService;
	@GetMapping
	public String viewAllCategory(Model model,Principal principal) {
		List<Category> listCategory = categoryservice.listAll();
		model.addAttribute("categories",listCategory);
		String username = principal.getName();
        User user = userService.findByUsername(username);
        List<CartItem> cartItems = cartItemService.getCartItems(user);
        model.addAttribute("count", cartItems.size());
		return "category/index";
	
	}
	
	@Autowired
	private BookServices services;
	
	
	@GetMapping("/create")
	public String showNewCategoryPage(Model model) {
		Category category = new Category();
		model.addAttribute("category",category);
		model.addAttribute("books",services.listAll());
		return "category/create";
	}
	@PostMapping("/save")
	public String saveCategory(@ModelAttribute("category")Category category) {
		categoryservice.save(category);
		return "redirect:/categories";
		
	}
	
	@GetMapping("/edit/{id}")
	public String showEditCAtegoryPage(@PathVariable("id")Long id,Model model) {
		Category category = categoryservice.get(id);
		if(category==null) {
			return "notfound";
		} else {
			model.addAttribute("categories", categoryservice.listAll());
			model.addAttribute("category",category);
			return "category/edit";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deletecategory(@PathVariable("id")Long id) {
		Category category = categoryservice.get(id);
		if(category==null) {
			return "notfound";
		} else {
			categoryservice.delete(id);
			return "redirect:/categories";
		}
	}
}
