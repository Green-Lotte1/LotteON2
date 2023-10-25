package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductEntity;
import co.kr.lotte.entity.product.ProductOrderEntity;
import co.kr.lotte.entity.product.ProductOrderItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    public Page<ProductEntity> findByProdCate1AndProdCate2(int cate1, int cate2, Pageable pageable);
    public List<ProductEntity> findAll();

    public List<ProductEntity> findTop5ByOrderByScoreDesc();
    public List<ProductEntity> findTop8ByOrderByScoreDesc();
    public List<ProductEntity> findTop8ByOrderByHitDesc();
    public List<ProductEntity> findTop8ByOrderByRdateDesc();
    public List<ProductEntity> findTop8ByOrderByDiscountDesc();

    public Page<ProductEntity> findByDeleteYn(String deleteYn, Pageable pageable);

    public int deleteByProdNo(Integer prodNo);
//    public Page<ProductEntity> findByProdNameLike(String prodName, Pageable pageable);
//    public Page<ProductEntity> findByProdNoLike(int prodNo, Pageable pageable);
//    public Page<ProductEntity> findByCompanyLike(String company, Pageable pageable);
//    public Page<ProductEntity> findBySellerLike(String seller, Pageable pageable);




}