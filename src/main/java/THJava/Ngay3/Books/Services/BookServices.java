package THJava.Ngay3.Books.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import THJava.Ngay3.Books.Models.Book;
import THJava.Ngay3.Books.Models.User;
import THJava.Ngay3.Books.Repositories.BookRepository;

@Service
@Transactional
public class BookServices {
	int pageSize = 4;
	@Autowired
	private BookRepository bookRepository;
	public Page<Book> listAll(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		return bookRepository.findAll(pageable);
	}
	public Page<Book> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
				sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		System.out.println(keyword);
		if (keyword != null) {
			return bookRepository.Search(pageable, keyword);
		}
		return bookRepository.findWithOutDelete(pageable);

	}
	public List<Book> listAll() {
		return bookRepository.findAll(Sort.by("title").ascending());
	}
	public Book save( Book product ) {
		bookRepository.save(product);
		return product;
	}
	
	public Book get(long id) {
		return bookRepository.findById(id).get();
	}
	
	public void delete(long id) {
		bookRepository.deleteById(id);
	}
}
