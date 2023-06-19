package THJava.Ngay3.Books.Repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import THJava.Ngay3.Books.Models.CartItem;
import THJava.Ngay3.Books.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.tokenforgotpassword = :token")
	public User getUserBytokenforgotpassword(String token);

	@Query("SELECT u FROM User u WHERE u.verificationCode = :code")
	public User findByVerificationCode(String code);
	
	List<User> getUserById(User id);
	
}
