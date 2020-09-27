package com.qualitychemicals.qciss.loan.dao;

import com.qualitychemicals.qciss.loan.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
}
