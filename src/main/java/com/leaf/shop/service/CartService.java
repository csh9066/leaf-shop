package com.leaf.shop.service;

import com.leaf.shop.domain.cart.Cart;
import com.leaf.shop.domain.cart.CartItem;
import com.leaf.shop.domain.cart.CartItemRepository;
import com.leaf.shop.domain.cart.CartRepository;
import com.leaf.shop.module.cart.AlreadyCartProductExistException;
import com.leaf.shop.dto.cart.AddCartItemDto;
import com.leaf.shop.module.common.exception.ResourceNotFoundException;
import com.leaf.shop.dto.cart.CartItemByBrandDto;
import com.leaf.shop.dto.cart.CartItemDto;
import com.leaf.shop.domain.product.Product;
import com.leaf.shop.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    public long createCart(long userId) {
        User user = userService.findUserById(userId);
        cartRepository.findByUserId(userId).ifPresent(
                (m) -> {
                    throw new AlreadyCartProductExistException("이미 카트가 존재합니다");
                }
        );
        Cart cart = Cart.create(user);
        cartRepository.save(cart);
        return cart.getId();
    }

    public Cart findCartByUserId(long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Cart", "userId", userId)
        );
    }

    public void addCartItem(AddCartItemDto addCartRequestDto, long userId) {
        Product product = productService.findProductById(addCartRequestDto.getProductId());
        if (product.getStock() < addCartRequestDto.getCount()) {
            throw new IllegalStateException("재고가 부족 합니다.");
        }

        Cart cart = findCartByUserId(userId);
        cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId() == product.getId())
                .findFirst()
                .ifPresentOrElse(
                        (i) -> {
                            i.changeCount(addCartRequestDto.getCount());
                        },
                        () -> {
                            CartItem newItem = CartItem.builder()
                                    .cart(cart)
                                    .product(product)
                                    .count(addCartRequestDto.getCount())
                                    .build();
                            cart.addCartItem(newItem);
                        }
                );
    }

    public void changeCartItemCount(Long cartItemId, int count) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        cartItem.ifPresent((ci) -> ci.changeCount(count));

    }

    public List<CartItemByBrandDto> findCartItems(long userId) {
        Cart fetchCart = cartRepository.findCartFetchJoinByUserId(userId);
        Map<Long, CartItemByBrandDto> cartItemByBrandDtoMap = new HashMap<>();

        fetchCart.getItems()
                .stream()
                .forEach((ci) -> {
                    long brandId = ci.getProduct().getBrand().getId();
                    CartItemByBrandDto cartItemByBrandDto;
                    if (cartItemByBrandDtoMap.containsKey(brandId)) {
                        cartItemByBrandDto = cartItemByBrandDtoMap.get(brandId);
                    } else {
                        cartItemByBrandDto = new CartItemByBrandDto(brandId, ci.getProduct().getBrand().getName());
                        cartItemByBrandDtoMap.put(brandId, cartItemByBrandDto);
                    }
                    CartItemDto cartItemDto = CartItemDto.builder()
                            .cartItemId(ci.getId())
                            .checked(ci.isChecked())
                            .productId(ci.getProduct().getId())
                            .price(ci.getProduct().getPrice())
                            .count(ci.getCount())
                            .productName(ci.getProduct().getName())
                            .build();
                    cartItemByBrandDto.addItem(cartItemDto);
                });
        return new ArrayList<>(cartItemByBrandDtoMap.values());
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void emptyCartItems(long userId, List<Long> productIds) {
        Cart cart = findCartByUserId(userId);
        List<Long> itemIds = new ArrayList<>();
        cart.getItems().forEach(
                i -> {
                    if (productIds.contains(i.getProduct().getId())) {
                        itemIds.add(i.getId());
                    }
                }
        );
        cartItemRepository.deleteAllByIdInBatch(itemIds);
    }

    public void checkCartItemForOrder(long userId, List<Long> checkCartItemDtos) {
        Cart cart = cartRepository.findByUserId(userId).get();
        List<CartItem> cartItems = cart.getItems();
        cartItems.forEach(i -> {
            if (checkCartItemDtos.contains(i.getId())) {
                i.setChecked(true);
            } else {
                i.setChecked(false);
            }
        });
    }
}
