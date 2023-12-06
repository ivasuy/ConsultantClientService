package com.vasu.ConsultantClientService.controller;

import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.model.request.ReviewRequest;
import com.vasu.ConsultantClientService.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review/add-review")
    public ResponseModel addReview(@RequestBody ReviewRequest reviewRequest){
        return reviewService.addReview(reviewRequest);
    }

    @GetMapping("/review/view-review")
    public ResponseModel viewReview(@RequestParam String sessionId){
        return  reviewService.viewReview(sessionId);
    }
}
