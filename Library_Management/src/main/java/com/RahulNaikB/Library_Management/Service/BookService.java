package com.RahulNaikB.Library_Management.Service;

import com.RahulNaikB.Library_Management.DTO.BookDTO;
import com.RahulNaikB.Library_Management.Entity.Book;
import com.RahulNaikB.Library_Management.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setIsAvailable(bookDTO.getIsAvailable());
        book.setQuantity(bookDTO.getQuantity());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setIsAvailable(bookDTO.getIsAvailable());
        existingBook.setQuantity(bookDTO.getQuantity());
        return bookRepository.save(existingBook);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
