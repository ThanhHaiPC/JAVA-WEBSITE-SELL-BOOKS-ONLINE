package THJava.Ngay3.Books.Services;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.User;
import THJava.Ngay3.Books.Repositories.CartItemRepository;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void addToCart(User user, Book book, int quantity) {
        CartItem cartItem = cartItemRepository.findByUserIdAndBookId(user.getId(), book.getId());
//qwqerqwer
        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + quantity;
            BigDecimal newTotal = book.getPrice().multiply(BigDecimal.valueOf(newQuantity));
            cartItem.setQuantity(newQuantity);
            cartItem.setTotal(newTotal);
        } else {
            cartItem = new CartItem();
            cartItem.setUserId(user.getId());
            cartItem.setName(book.getTitle());
            cartItem.setBrand(book.getAuthor());
            cartItem.setPrice(book.getPrice()); // Assuming book.getPrice() returns a BigDecimal
            cartItem.setQuantity(quantity);
            cartItem.setImageUrl(book.getPhotourl());
            cartItem.setTotal(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cartItem.setBookId(book.getId());
            cartItemRepository.save(cartItem);
        }
    }


    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            BigDecimal newTotal = cartItem.getPrice().multiply(BigDecimal.valueOf(quantity));
            cartItem.setQuantity(quantity);
            cartItem.setTotal(newTotal);
            cartItemRepository.save(cartItem);
        }
    }

    public BigDecimal getTotalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getTotal());
        }
        return totalPrice;
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUserId(user.getId());
    }
}
