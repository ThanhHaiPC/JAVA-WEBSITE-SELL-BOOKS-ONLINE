	package THJava.Ngay3.Books.Models;
	
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.ManyToOne;
	import javax.persistence.Table;
	
	import THJava.Ngay3.Books.Models.Book;
	
	@Entity
	@Table(name = "cart_item")
	public class CartItem {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    @ManyToOne
	    @JoinColumn(name = "cart_id")
	    private Cart cart;
	
	    @ManyToOne
	    @JoinColumn(name = "book_id")
	    private Book book;
	
	    @Column(name = "quantity")
	    private int quantity;
	
		public CartItem(Long id, Cart cart, Book book, int quantity) {
			super();
			this.id = id;
			this.cart = cart;
			this.book = book;
			this.quantity = quantity;
		}
	
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		public Cart getCart() {
			return cart;
		}
	
		public void setCart(Cart cart) {
			this.cart = cart;
		}
		public Book getBook() {
			return book;
		}
	
		public void setBook(Book book) {
			this.book = book;
		}
	
		public int getQuantity() {
			return quantity;
		}
	
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	
		public CartItem() {
			super();
		}
	    
	}
