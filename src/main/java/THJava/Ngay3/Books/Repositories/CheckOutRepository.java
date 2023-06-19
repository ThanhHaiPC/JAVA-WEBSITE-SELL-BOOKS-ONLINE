package THJava.Ngay3.Books.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.OrderDetal;
import THJava.Ngay3.Books.Models.User;

public interface CheckOutRepository extends JpaRepository<OrderDetal, Long> {

	OrderDetal findByUserIdAndBookId(Long userId, Long bookId);
	 List<OrderDetal> findByUserId(Long userId);
}
