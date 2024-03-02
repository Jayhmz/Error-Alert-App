package com.plantaccion.alartapp.admin.staff.controller;

import com.plantaccion.alartapp.admin.staff.service.DeleteStaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/admin")
@CrossOrigin(origins = {"https://scripting-app-frontend.vercel.app", "http://localhost:5173"})
public class DeleteStaffsController {
    private final DeleteStaffService deleteStaffService;

    public DeleteStaffsController(DeleteStaffService deleteStaffService) {
        this.deleteStaffService = deleteStaffService;
    }

    @DeleteMapping("/soft-delete-staff/{id}")
    public ResponseEntity<?> softDeleteStaff(@PathVariable("id") Long id){
        deleteStaffService.softDelete(id);
        return new ResponseEntity<>("Staff deactivated successfully", HttpStatus.OK);
    }

    @PostMapping("/activate-staff/{id}")
    public ResponseEntity<?> activateStaff(@PathVariable("id") Long id){
        deleteStaffService.activate(id);
        return new ResponseEntity<>("Staff reactivated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/hard-delete-staff/{id}")
    public ResponseEntity<?> hardDeleteStaff(@PathVariable("id") Long id){
        deleteStaffService.hardDelete(id);
        return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
    }
}
