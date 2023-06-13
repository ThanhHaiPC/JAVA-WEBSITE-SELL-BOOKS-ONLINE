package THJava.Ngay3.Books.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Models.Cart;
import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Repositories.CartItemRepository;
import THJava.Ngay3.Books.Repositories.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private CartRepository cartRepository;
	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
	}
	public void removeItemFromCart(Cart cart, CartItem cartItem) {
	    cart.removeItem(cartItem); // Assuming you have implemented the removeCartItem method in the Cart class
	    cartRepository.save(cart);
	}
	public Cart getCartById(Long cartId) {
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		return cartOptional.orElse(null);
	}

	public void addItemToCart(Cart cart,Book book, int quantity) {
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setQuantity(quantity);

		cart.getCartItems().add(cartItem);

		cartRepository.save(cart);
	}

	public void removeItemFromCart(CartItem cartItem) {
		Cart cart = cartItem.getCart();
		List<CartItem> cartItems = cart.getCartItems();    
		cartItems.remove(cartItem);
		cartRepository.save(cart);
	}

	public void updateItemQuantity(CartItem cartItem, int quantity) {
		cartItem.setQuantity(quantity);

		Cart cart = cartItem.getCart();
		cartRepository.save(cart);
	}

	public void clearCart(Cart cart) {
		List<CartItem> cartItems = cart.getCartItems();
		cartItems.clear();

		// Lưu giỏ hàng vào cơ sở dữ liệu
		//cartRepository.save(cart);
	}
}
