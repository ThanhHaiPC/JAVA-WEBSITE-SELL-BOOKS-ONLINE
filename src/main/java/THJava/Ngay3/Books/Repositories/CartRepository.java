package THJava.Ngay3.Books.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import THJava.Ngay3.Books.Models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
