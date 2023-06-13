package THJava.Ngay3.Books.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Services.CartItemService;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{cartItemId}")
    public CartItem getCartItemById(@PathVariable Long cartItemId) {
        return cartItemService.getCartItemById(cartItemId);
    }

    // Other controller methods for cart items
}
