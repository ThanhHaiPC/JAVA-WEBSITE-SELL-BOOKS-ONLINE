package THJava.Ngay3.Books.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import THJava.Ngay3.Books.Models.Cart;
import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Services.CartService;
import THJava.Ngay3.Books.Services.CartItemService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    @Autowired
    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @PostMapping("/{cartId}/addItem")
    public String addItemToCart(@PathVariable Long cartId, @RequestBody Book book, @RequestParam int quantity) {
        Cart cart = cartService.getCartById(cartId);
        cartService.addItemToCart(cart, book, quantity);
        return "cart/index";
    }

    @DeleteMapping("/{cartId}/removeItem/{cartItemId}")
    public String removeItemFromCart(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        Cart cart = cartService.getCartById(cartId);
        Optional<CartItem> cartItem = cartItemService.findCartItemById(cartItemId);
        cartItem.ifPresent(item -> cartService.removeItemFromCart(cart, item));
        return "cart/index";
    }

    @PutMapping("/{cartId}/updateItem/{cartItemId}")
    public String updateItemQuantity(@PathVariable Long cartId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        Cart cart = cartService.getCartById(cartId);
        Optional<CartItem> cartItem = cartItemService.findCartItemById(cartItemId);
        cartItem.ifPresent(item -> cartService.updateItemQuantity(item, quantity));
        return "cart/index";
    }

    @DeleteMapping("/{cartId}/clear")
    public String clearCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        cartService.clearCart(cart);
        return "cart/index";
    }
}

