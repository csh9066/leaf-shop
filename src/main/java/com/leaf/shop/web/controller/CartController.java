package com.leaf.shop.web.controller;

import com.leaf.shop.service.CartService;
import com.leaf.shop.module.cart.dto.AddCartItemDto;
import com.leaf.shop.module.cart.dto.CartItemByBrandDto;
import com.leaf.shop.module.cart.dto.ChangeCartItemCountDto;
import com.leaf.shop.security.AuthUser;
import com.leaf.shop.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/cart")
@RestController
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public void createCart(@AuthUser UserPrincipal user) {
        cartService.createCart(user.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/item")
    public void addCartItem(@AuthUser UserPrincipal user, @Valid @RequestBody AddCartItemDto addCartRequestDto) {
        cartService.addCartItem(addCartRequestDto, user.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<CartItemByBrandDto> findCartItemByBrand(@AuthUser UserPrincipal user) {
        return cartService.findCartItems(user.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/item/{cartItemId}")
    public void changeCartItemCount(@Valid @RequestBody ChangeCartItemCountDto changeCartItemCountDto,
                                    @PathVariable("cartItemId") Long cartItemId) {
        cartService.changeCartItemCount(cartItemId, changeCartItemCountDto.getCount());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/item/{cartItemId}")
    public void deleteCartItems(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/item/check")
    public void checkCartItemToOrder(@RequestBody List<Long> checkCartItemDtos,
                               @AuthUser UserPrincipal user) {
        cartService.checkCartItemForOrder(user.getUserId(),checkCartItemDtos);
    }

}
