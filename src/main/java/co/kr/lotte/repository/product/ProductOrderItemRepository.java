package co.kr.lotte.repository.product;

import co.kr.lotte.dto.product.ProductOrderItemDTO;
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

    // MyPage 추가
    public List<ProductOrderItemEntity> findTop1ByOrdUidOrderByOrdDateDesc(String ordUid);

    public Long countByOrdUidAndOrdStatusNot(String uid, String ordStatus);
}