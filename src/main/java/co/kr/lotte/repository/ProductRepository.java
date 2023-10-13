package co.kr.lotte.repository;

import co.kr.lotte.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    public Page<ProductEntity> findByProdCate1AndProdCate2(int cate1, int cate2, Pageable pageable);
}