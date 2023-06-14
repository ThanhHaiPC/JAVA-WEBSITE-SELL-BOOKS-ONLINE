package THJava.Ngay3.Books.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import THJava.Ngay3.Books.Models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserIdAndBookId(Long userId, Long bookId);
    List<CartItem> findByUserId(Long userId);
}
