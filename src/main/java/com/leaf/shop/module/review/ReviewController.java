package com.leaf.shop.module.review;

import com.leaf.shop.module.review.dto.ReviewDto;
import com.leaf.shop.security.AuthUser;
import com.leaf.shop.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReviewController {
    public final ReviewService reviewService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("my-reviews")
    public List<ReviewDto> findMyReviews(@AuthUser UserPrincipal user) {
        return reviewService.findMyReviews(user.getUserId());
    }

    @GetMapping("product-reviews")
    public List<ReviewDto> findProductReviews(@RequestParam(name = "product_id") Long productId) {
        return reviewService.findReviewsByProduct(productId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("my-reviews/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
