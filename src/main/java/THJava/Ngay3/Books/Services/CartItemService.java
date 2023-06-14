package THJava.Ngay3.Books.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Models.CartItem;

@Service
public class CartItemService {
    private Map<Long, CartItem> cartItems = new HashMap<>();

    public void addToCart(Book product, int quantity) {
        CartItem cartItem = cartItems.get(product.getId());
        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(newQuantity);
            cartItem.setTotal(cartItem.getPrice() * newQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setId(product.getId());
            cartItem.setName(product.getTitle());
            cartItem.setBrand(product.getAuthor());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(quantity);
            cartItem.setImageUrl(product.getPhotourl());
            cartItem.setTotal(product.getPrice() * quantity);
            cartItems.put(product.getId(), cartItem);
        }
    }

    public void removeFromCart(Long productId) {
        cartItems.remove(productId);
    }

    public void updateQuantity(Long productId, int quantity) {
        CartItem cartItem = cartItems.get(productId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItem.setTotal(cartItem.getPrice() * quantity);
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public Long getTotalPrice() {
        Long totalPrice = 0L;
        for (CartItem cartItem : cartItems.values()) {
            totalPrice += cartItem.getTotal();
        }
        return totalPrice;
    }
}
