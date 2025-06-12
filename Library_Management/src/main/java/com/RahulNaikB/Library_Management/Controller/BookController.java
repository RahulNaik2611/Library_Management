package com.RahulNaikB.Library_Management.Controller;


import com.RahulNaikB.Library_Management.DTO.BookDTO;
import com.RahulNaikB.Library_Management.Entity.Book;
import com.RahulNaikB.Library_Management.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController
{
    @Autowired
    private BookService bookService;


    @GetMapping("/getallbooks")
    public ResponseEntity<List<Book>> getAllBooks()
    {
        return  ResponseEntity.ok(bookService.getAllBooks());
    }


    @GetMapping("/getbookbyid/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id)
    {
        return ResponseEntity.ok(bookService.getBookById(id));
    }


    @PostMapping("/addbook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO)
    {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PutMapping("/updatebook/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Book> updateBook(@PathVariable Long id ,@RequestBody BookDTO bookDTO)
    {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));

    }


    @DeleteMapping("/deletebook/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id)
    {
        bookService.deleteBookId(id);
        return  ResponseEntity.ok().build();
    }
}
