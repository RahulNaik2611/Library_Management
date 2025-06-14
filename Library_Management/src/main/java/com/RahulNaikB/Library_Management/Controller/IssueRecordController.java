package com.RahulNaikB.Library_Management.Controller;

import com.RahulNaikB.Library_Management.Entity.IssueRecord;
import com.RahulNaikB.Library_Management.Service.IssueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issuerecords")
public class IssueRecordController {

    @Autowired
    private IssueRecordService issueRecordService;

    @PostMapping("/issue/{bookId}")
    public ResponseEntity<IssueRecord> issueTheBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(issueRecordService.issueTheBook(bookId));
    }

    @PostMapping("/return/{issueRecordId}")
    public ResponseEntity<IssueRecord> returnTheBook(@PathVariable Long issueRecordId) {
        return ResponseEntity.ok(issueRecordService.returnTheBook(issueRecordId));
    }
}
