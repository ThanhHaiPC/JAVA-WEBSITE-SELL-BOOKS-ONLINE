package THJava.Ngay3.Books.Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.User;
import THJava.Ngay3.Books.Services.BookServices;
import THJava.Ngay3.Books.Services.CartItemService;
import THJava.Ngay3.Books.Services.UserService;
import THJava.Ngay3.Books.Utils.FileUploadUtil;

@Controller
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookServices bookService;

    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
    	String username = principal.getName();
        User user = userService.findByUsername(username);
        List<CartItem> cartItems = cartItemService.getCartItems(user);
        BigDecimal totalPrice = cartItemService.getTotalPrice(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItemCount", cartItems.size());
        return "book/cart";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book, @RequestParam("image") MultipartFile multipartFile)
            throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        book.setPhotourl(fileName);
        Book savedBook = bookService.save(book);
        if (!multipartFile.isEmpty()) {
            String uploadDir = "photos/" + savedBook.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, Principal principal) {
        String username = principal.getName(); // Lấy tên người dùng từ phiên đăng nhập
        User user = userService.findByUsername(username); // Tìm người dùng trong cơ sở dữ liệu
        if (user != null) {
            Book product = bookService.get(productId);
            if (product != null) {
                cartItemService.addToCart(user, product, quantity); // Truyền thông tin người dùng vào phương thức addToCart
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartItemService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{productId}")
    public String updateCartItem(@PathVariable Long productId, @RequestParam("quantity") int quantity) {
        cartItemService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }
}