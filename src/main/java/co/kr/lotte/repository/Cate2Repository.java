package co.kr.lotte.repository;

import co.kr.lotte.entity.product.ProductCate2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cate2Repository extends JpaRepository<ProductCate2Entity, Integer> {
    
}