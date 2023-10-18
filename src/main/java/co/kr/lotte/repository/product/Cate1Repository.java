package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductCate1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cate1Repository  extends JpaRepository<ProductCate1Entity, Integer> {

    public List<ProductCate1Entity> findByCate1(Integer cate1);
}