package co.kr.lotte.repository.product;
import co.kr.lotte.entity.product.ProductOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Integer> {
    public Page<ProductOrderEntity> findByOrdUidAndOrdDateBetween(String uid, LocalDateTime begin, LocalDateTime end, Pageable pageable);
}