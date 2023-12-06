package com.vasu.ConsultantClientService.service.implementation;

import com.vasu.ConsultantClientService.entity.Review;
import com.vasu.ConsultantClientService.entity.Session;
import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.model.request.ReviewRequest;
import com.vasu.ConsultantClientService.repository.ReviewRepository;
import com.vasu.ConsultantClientService.repository.SessionRepository;
import com.vasu.ConsultantClientService.service.ReviewService;
import com.vasu.ConsultantClientService.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public ResponseModel addReview(ReviewRequest reviewRequest) {
        Long sessionId = HashUtils.decode(reviewRequest.getSessionId());
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found for id: " + sessionId));

        Review review = Review.builder()
                .review(reviewRequest.getReview())
                .session(session)
                .build();
        reviewRepository.save(review);

        return ResponseModel.builder()
                .status(1L)
                .message("Review For The Session Created SuccessFully")
                .data(null)
                .build();
    }

    @Override
    public ResponseModel viewReview(String sessionId) {
        Long currSessionId = HashUtils.decode(sessionId);
        Session session = sessionRepository.findById(currSessionId)
                .orElseThrow(() -> new RuntimeException("Session not found for id: " + sessionId));

        Review review = reviewRepository.findBySessionId(session.getSessionId());

        return ResponseModel.builder()
                .status(1L)
                .message("Review For The Session")
                .data(review.getReview())
                .build();
    }
}
