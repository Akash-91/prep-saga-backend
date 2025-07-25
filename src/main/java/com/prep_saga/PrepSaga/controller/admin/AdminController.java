package com.prep_saga.PrepSaga.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Sample in Controller
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/data")
    public ResponseEntity<String> getAdminData() {
        return ResponseEntity.ok("This is admin-only data");
    }

}
