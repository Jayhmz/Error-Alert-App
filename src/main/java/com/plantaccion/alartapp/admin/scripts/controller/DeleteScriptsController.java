package com.plantaccion.alartapp.admin.scripts.controller;

import com.plantaccion.alartapp.admin.scripts.service.DeleteScriptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/admin")
public class DeleteScriptsController {
    private final DeleteScriptService deleteScriptService;

    public DeleteScriptsController(DeleteScriptService deleteScriptService) {
        this.deleteScriptService = deleteScriptService;
    }

    @DeleteMapping("/soft-delete-script/{id}")
    public ResponseEntity<?> softDeleteScript(@PathVariable("id") Long id) {
        deleteScriptService.softDeleteById(id);
        return new ResponseEntity<>("Record soft-deactivated successfully", HttpStatus.OK);
    }

    @PostMapping("/activate-script/{id}")
    public ResponseEntity<?> activateScript(@PathVariable("id") Long id) {
        deleteScriptService.activateScriptById(id);
        return new ResponseEntity<>("Record activated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/hard-delete-script/{id}")
    public ResponseEntity<?> hardDeleteScript(@PathVariable("id") Long id) {
        deleteScriptService.hardDeleteById(id);
        return new ResponseEntity<>("Record removed successfully", HttpStatus.OK);
    }
}
