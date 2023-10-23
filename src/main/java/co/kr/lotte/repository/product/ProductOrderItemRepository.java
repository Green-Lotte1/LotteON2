package co.kr.lotte.repository.product;

import co.kr.lotte.entity.product.ProductOrderItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItemEntity, Integer> {
    public List<ProductOrderItemEntity> findByOrdNo(int ordNo);
    public Page<ProductOrderItemEntity> findByOrdUidAndOrdDateBetween(String uid, LocalDateTime begin, LocalDateTime end, Pageable pageable);
}