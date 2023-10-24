package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Integer> {
    public ProductReviewEntity findByProdNoAndUid(int prodNo, String uid);
}
