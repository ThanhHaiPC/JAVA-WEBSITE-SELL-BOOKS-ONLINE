package THJava.Ngay3.Books.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Repositories.CartItemRepository;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public Optional<CartItem> findCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }
    public CartItem getCartItemById(Long cartItemId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        return cartItemOptional.orElse(null);
    }
    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    public void updateItemQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
}