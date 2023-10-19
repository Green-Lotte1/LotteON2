package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductCate2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cate2Repository extends JpaRepository<ProductCate2Entity, Integer> {
    public ProductCate2Entity findByCate1AndCate2(int cate1, int cate2);

    public List<ProductCate2Entity> findByCate2(int cate2);
    
}