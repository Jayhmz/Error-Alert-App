package com.plantaccion.alartapp.rch.controller;

import com.plantaccion.alartapp.admin.staff.service.DeleteStaffService;
import com.plantaccion.alartapp.rch.service.DeleteICOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/rch")
@CrossOrigin(origins = {"https://scripting-app-frontend.vercel.app", "http://localhost:5173"})
public class DeleteICOController {
    private final DeleteICOService deleteIcoService;

    public DeleteICOController(DeleteICOService deleteIcoService) {
        this.deleteIcoService = deleteIcoService;
    }

    @DeleteMapping("/soft-delete-staff/{id}")
    public ResponseEntity<?> softDeleteStaff(@PathVariable("id") String id){
        deleteIcoService.softDelete(id);
        return new ResponseEntity<>("Staff deactivated successfully", HttpStatus.OK);
    }

    @PostMapping("/activate-staff/{id}")
    public ResponseEntity<?> activateStaff(@PathVariable("id") String id){
        deleteIcoService.activate(id);
        return new ResponseEntity<>("Staff reactivated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/hard-delete-staff/{id}")
    public ResponseEntity<?> hardDeleteStaff(@PathVariable("id") String id){
        deleteIcoService.hardDelete(id);
        return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
    }
}
