package com.leaf.shop.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(@Param("userId") long userId);
    @Query("select c from Cart c left join fetch c.items i left join fetch i.product p left join fetch p.brand where c.user.id = :userId")
    Cart findCartFetchJoinByUserId(@Param("userId") long userId);
    @Query("select c from Cart c left join fetch c.items i left join fetch i.product p left join fetch p.brand where c.user.id = :userId and i.checked = true")
    Cart findCartFetchJoinByUserIdAndItemsCheckedTrue(@Param("userId") long userId);
}
