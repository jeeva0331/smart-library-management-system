package com.smartLibrary.controller;

import com.smartLibrary.model.BorrowRecord;
import com.smartLibrary.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin(origins = "*")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/issue")
    public BorrowRecord borrowBook(@RequestParam Long bookId,
                                    @RequestParam Long memberId) {
        return borrowService.borrowBook(bookId, memberId);
    }

    @PutMapping("/return/{recordId}")
    public BorrowRecord returnBook(@PathVariable Long recordId) {
        return borrowService.returnBook(recordId);
    }

    @GetMapping
    public List<BorrowRecord> getAllRecords() {
        return borrowService.getAllRecords();
    }

    @GetMapping("/active")
    public List<BorrowRecord> getActiveRecords() {
        return borrowService.getActiveRecords();
    }

    @GetMapping("/member/{memberId}")
    public List<BorrowRecord> getByMember(@PathVariable Long memberId) {
        return borrowService.getRecordsByMember(memberId);
    }
}