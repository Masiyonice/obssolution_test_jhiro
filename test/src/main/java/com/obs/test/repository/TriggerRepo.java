package com.obs.test.repository;

import com.obs.test.utils.TriggerSequenceForOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerRepo extends JpaRepository<TriggerSequenceForOrder, Integer> {
    @Query(value = "SELECT nextval('order_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
