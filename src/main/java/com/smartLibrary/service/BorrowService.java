package com.smartLibrary.service;

import com.smartLibrary.model.Book;
import com.smartLibrary.model.BorrowRecord;
import com.smartLibrary.model.Member;
import com.smartLibrary.repository.BookRepository;
import com.smartLibrary.repository.BorrowRecordRepository;
import com.smartLibrary.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Borrow a book
    public BorrowRecord borrowBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getAvailableQty() <= 0) {
            throw new RuntimeException("Book not available");
        }

        // Reduce available quantity
        book.setAvailableQty(book.getAvailableQty() - 1);
        bookRepository.save(book);

        // Create borrow record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14)); // 14 days to return
        record.setFine(0);

        return borrowRecordRepository.save(record);
    }

    // Return a book
    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        LocalDate returnDate = LocalDate.now();
        record.setReturnDate(returnDate);

        // Calculate fine — ₹5 per day overdue
        if (returnDate.isAfter(record.getDueDate())) {
            long overdueDays = record.getDueDate().until(returnDate).getDays();
            record.setFine(overdueDays * 5.0);
        }

        // Increase available quantity
        Book book = record.getBook();
        book.setAvailableQty(book.getAvailableQty() + 1);
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getAllRecords() {
        return borrowRecordRepository.findAll();
    }

    public List<BorrowRecord> getActiveRecords() {
        return borrowRecordRepository.findByReturnDateIsNull();
    }

    public List<BorrowRecord> getRecordsByMember(Long memberId) {
        return borrowRecordRepository.findByMemberId(memberId);
    }
}