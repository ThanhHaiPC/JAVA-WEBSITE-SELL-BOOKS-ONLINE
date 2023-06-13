package THJava.Ngay3.Books.Controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import THJava.Ngay3.Books.Models.User;

@Controller
public class HomeController {
	@GetMapping("/")
	public String Home() {
		return "home/index";
	
	
	}

}
