package com.vasu.ConsultantClientService.service;

import com.vasu.ConsultantClientService.model.response.ResponseModel;
import com.vasu.ConsultantClientService.model.request.ReviewRequest;

public interface ReviewService {

    ResponseModel addReview(ReviewRequest reviewRequest);

    ResponseModel viewReview(String sessionId);
}
