package com.plantaccion.alartapp.ico.controller;

import com.plantaccion.alartapp.ico.dto.ResolutionDTO;
import com.plantaccion.alartapp.ico.service.WriteAlertReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/ico")
public class WriteAlertReviewController {
    private final WriteAlertReviewService alertReviewService;


    public WriteAlertReviewController(WriteAlertReviewService alertReviewService) {
        this.alertReviewService = alertReviewService;
    }

    @PostMapping("/claim/{alertId}")
    public ResponseEntity<?> assignAlertToUser(@PathVariable String alertId) {
        if (alertReviewService.assignAlertToUser(alertId)) {
            return new ResponseEntity<>("Alert assigned successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to assign alert.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-review/{alertId}")
    public ResponseEntity<?> createReviewForAssignedAlert(@PathVariable String alertId, @RequestBody ResolutionDTO resolution) {
        if (alertReviewService.submitAlertReview(resolution, alertId)) {
            return new ResponseEntity<>("Review created successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to create review.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-review/{alertId}")
    public ResponseEntity<?> updateAssignedAlert(@PathVariable String alertId, @RequestBody ResolutionDTO resolution) {
        if (alertReviewService.updateAlertReview(resolution, alertId)) {
            return new ResponseEntity<>("Review updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update review.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
