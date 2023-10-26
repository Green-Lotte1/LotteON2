package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Integer> {
    public ProductReviewEntity findByProdNoAndUid(int prodNo, String uid);
    public Page<ProductReviewEntity> findByUid(String uid, Pageable pageable);
    public List<ProductReviewEntity> findByUid(String uid);
    public Page<ProductReviewEntity> findByProdNo(int prodNo, Pageable pageable);

}
