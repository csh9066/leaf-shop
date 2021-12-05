package com.leaf.shop.module.review;

import com.leaf.shop.module.common.exception.ResourceNotFoundException;
import com.leaf.shop.module.order.OrderItem;
import com.leaf.shop.module.order.OrderItemRepository;
import com.leaf.shop.module.product.Product;
import com.leaf.shop.module.product.ProductRepository;
import com.leaf.shop.module.review.dto.CreateReviewDto;
import com.leaf.shop.module.review.dto.ReviewDto;
import com.leaf.shop.module.user.User;
import com.leaf.shop.module.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

        @Transactional
        public void writeReview(Long userId, CreateReviewDto createReviewDto) {
            OrderItem orderItem = orderItemRepository.findById(createReviewDto.getOrderItemId()).orElseThrow(
                    () -> new ResourceNotFoundException("orderItem", "id" ,createReviewDto.getOrderItemId())
            );
            if (!orderItem.getIsWriteReview()) {
                throw new IllegalStateException("리뷰 가능한 주문이 아닙니다.");
            }
            orderItem.afterWriteReview();

            Product product = productRepository.findById(orderItem.getProduct().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("orderItem", "id" ,createReviewDto.getOrderItemId())
            );
            User user = userService.findUserById(userId);

            Review review = new Review(createReviewDto.getContent(), user);
            product.writeReview(review);
        }

        @Transactional
        public void deleteReview(Long reviewId) {
            Review review = reviewRepository.findById(reviewId).get();
            reviewRepository.delete(review);
        }

        @Transactional
        public void modifyReview(Long reviewId, String content  ) {
            Review review = reviewRepository.getById(reviewId);
            review.modify(content);
        }

        public List<ReviewDto> findMyReviews(Long userId) {
            List<Review> reviews = reviewRepository.findByUserId(userId);
            List<ReviewDto> result = reviews.stream()
                    .map(ReviewDto::new)
                    .collect(Collectors.toList());
            return result;
        }

        public List<ReviewDto> findReviewsByProduct(Long productId) {
            List<Review> reviews = reviewRepository.findByProductId(productId);
            List<ReviewDto> result = reviews.stream()
                    .map(ReviewDto::new)
                    .collect(Collectors.toList());
            return result;
        }

}
