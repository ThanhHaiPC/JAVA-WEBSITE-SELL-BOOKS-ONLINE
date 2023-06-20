package THJava.Ngay3.Books.Controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import THJava.Ngay3.Books.Services.SendMailService;
import THJava.Ngay3.Books.Exception.UserNotFoundException;
import THJava.Ngay3.Books.Models.CustomUserDetails;
import THJava.Ngay3.Books.Models.User;
import THJava.Ngay3.Books.Services.RoleService;
import THJava.Ngay3.Books.Services.UserService;
import THJava.Ngay3.Books.Utils.FileUploadUtil;
import THJava.Ngay3.Books.Utils.Utilities;
import net.bytebuddy.utility.RandomString;
@Controller
@ComponentScan("THJava.Ngay3.Books")
@ComponentScan("THJava.Ngay3.Books.Utils")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SendMailService sendMailService;

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("user", new User());

		return "auth/signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.addRoles(roleService.getbyName("USER"));
		user.setVerificationCode(RandomString.make(30));
		// user.setEnabled(true);
		userService.save(user);
		sendMailService.sendVerificationEmail(user, Utilities.getSiteURL(request));

		return "auth/register_success";
	}

	@GetMapping("/auth/me")
	public String findMe(Authentication authentication, Model model) {
		User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

		if (user == null) {
			return "notfound";

		} else {
			model.addAttribute("roles", roleService.listAll());
			model.addAttribute("user", user);
			return "user/edit";
		}
	}

	@GetMapping("/login")
	public String Login() {
		return "auth/login";
	}

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "auth/forgot_password_form";
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(30);

		try {
			userService.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utilities.getSiteURL(request) + "/reset_password?token=" + token;
			sendMailService.sendEmailForgotPassword(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (UserNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}

		return "auth/forgot_password_form";
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		User user = userService.getUserByTokenforgotpassWord(token);
		model.addAttribute("token", token);

		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "message";
		}

		return "auth/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		User user = userService.getUserByTokenforgotpassWord(token);
		model.addAttribute("title", "Reset your password");

		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "message";
		} else {
			userService.updatePassword(user, password);

			model.addAttribute("message", "You have successfully changed your password.");
		}

		return "auth/reset_password_form";
	}

	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code, Model model) {
		if (userService.verify(code)) {
			model.addAttribute("message", "Congratulations, your account has been verified.");
		} else {
			model.addAttribute("error", "Sorry, we could not verify account. It maybe already verified,\n"
					+ "        or verification code is incorrect.");
		}
		return "auth/result_Verify_form";
	}
	@GetMapping("/account")
	public String inforUser(Model model,Principal principal) {	
		String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("USERNAME", user.getUsername());
        model.addAttribute("EMAIL", user.getEmail());
        model.addAttribute("ID", user.getId());
        model.addAttribute("PHOTO", user.getphotourl());
        model.addAttribute("ROLE", user.getRoles());
		return "auth/inforUser";
	}
	
	
	
	
	@GetMapping("/change_password")
	public String changePasswordForm(Model model,Principal principal) {
		String username = principal.getName();
        User user = userService.findByUsername(username);				
		return "auth/change_password_form";
	}

	@PostMapping("/change_password")
	public String processChangePassword(HttpServletRequest request, Model model,Principal principal) {
		
		String password = request.getParameter("password");

		String username = principal.getName();
        User user = userService.findByUsername(username);
		model.addAttribute("title", "Reset your password");
		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "message";
		} else {
			userService.updatePassword(user, password);

			model.addAttribute("message", "You have successfully changed your password.");
		}

		return "auth/change_password_form";
	}
	@GetMapping("/edit/{id}")
	public String showEditUserPage(@PathVariable("id") Long id, Model model) {
		User user = userService.get(id);
		
		if(user == null) {
			return "notfound";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleService.listAll());
			return "auth/edit";
		}
	}
	@PostMapping("/saveEdit")
	public String saveUser(@ModelAttribute("user") User user, @RequestParam("image")  MultipartFile  multipartFile) 
			throws IOException{
		
		if (user.getPassword().isEmpty()) {
			user.setPassword(userService.get(user.getId()).getPassword());
		}else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setPhotourl(fileName);
        user.setEnabled(true);
        User saveUser = userService.save(user);
        if (!multipartFile.getOriginalFilename().isBlank())
        {
            String uploadDir = "photos/" + saveUser.getId();
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }
        userService.save(user);
		
		return "redirect:/account";
	}
}
