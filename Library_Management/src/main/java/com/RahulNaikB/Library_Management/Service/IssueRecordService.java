package com.RahulNaikB.Library_Management.Service;

import com.RahulNaikB.Library_Management.Entity.Book;
import com.RahulNaikB.Library_Management.Entity.IssueRecord;
import com.RahulNaikB.Library_Management.Entity.User;
import com.RahulNaikB.Library_Management.Repository.BookRepository;
import com.RahulNaikB.Library_Management.Repository.IssueRecordRepository;
import com.RahulNaikB.Library_Management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueRecordService {

    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public IssueRecord issueTheBook(Long bookId) {
        // Fetch the book by ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        // Check if the book is available
        if (book.getQuantity() <= 0 || !book.getIsAvailable()) {
            throw new RuntimeException("Book is not available for issue.");
        }

        // Get current authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the user by username
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Create and populate IssueRecord
        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setReturnDate(LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);
        issueRecord.setUser(user);
        issueRecord.setBook(book);

        // Update book quantity and availability
        book.setQuantity(book.getQuantity() - 1);
        if (book.getQuantity() == 0) {
            book.setIsAvailable(false);
        }

        // Save changes
        bookRepository.save(book);
        return issueRecordRepository.save(issueRecord);
    }

    public IssueRecord returnTheBook(Long issueRecordId) {
        // Corrected method: findById instead of findAllById
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(() -> new RuntimeException("Issue Record not found with ID: " + issueRecordId));

        // Check if already returned
        if (issueRecord.getIsReturned()) {
            throw new RuntimeException("Book already returned.");
        }

        // Update the book's quantity and availability
        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setIsAvailable(true);
        bookRepository.save(book);

        // Update the issue record
        issueRecord.setReturnDate(LocalDate.now());
        issueRecord.setIsReturned(true);

        return issueRecordRepository.save(issueRecord);
    }
}
