package com.leaf.shop.domain.product;

import com.leaf.shop.domain.brand.Brand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    public static final String NAME = "상품A";
    public static final String DESCRIPTION = "상품 설명";
    public static final String DETAIL = "상품 상세";
    public static final int PRICE = 3000;
    public static final String THUMBNAIL_URL = "https://thumurl";
    public Brand BRAND = new Brand("리프", "친환경 브랜드");

    @Test
    @DisplayName("생성 성공")
    void create() {
        Product product = Product.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .detail(DETAIL)
                .price(PRICE)
                .brand(BRAND)
                .thumbnailUrl(THUMBNAIL_URL)
                .build();

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(product.getDetail()).isEqualTo(DETAIL);
        assertThat(product.getThumbnailUrl()).isEqualTo(THUMBNAIL_URL);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getBrand()).isEqualTo(BRAND);
    }

    @Test
    @DisplayName("업데이트 성공")
    void update() {
        Product product = Product.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .detail(DETAIL)
                .price(PRICE)
                .brand(BRAND)
                .build();

        String nameToUpdate = "변경이름1";
        String descToUpdate = "변경설명";
        String detailToUpdate = "변경상세";
        String thumbUrlToUpdate = "https://thumurl/update";
        int priceToUpdate = 2000;

        product.update(nameToUpdate, priceToUpdate, descToUpdate, detailToUpdate, thumbUrlToUpdate);

        assertThat(product.getName()).isEqualTo(nameToUpdate);
        assertThat(product.getDescription()).isEqualTo(descToUpdate);
        assertThat(product.getDetail()).isEqualTo(detailToUpdate);
        assertThat(product.getThumbnailUrl()).isEqualTo(thumbUrlToUpdate);
        assertThat(product.getPrice()).isEqualTo(priceToUpdate);
    }



}
