package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCartEntity, Integer> {
    public ProductCartEntity findByUidAndProdNo(String uid, int prodNo);
    public List<ProductCartEntity> findByUid(String uid);
}