package com.obs.test.repository;

import com.obs.test.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReporsitory extends JpaRepository<Inventory, Integer> {
}
