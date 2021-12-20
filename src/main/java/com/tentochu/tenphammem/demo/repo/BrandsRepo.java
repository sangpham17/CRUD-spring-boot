package com.tentochu.tenphammem.demo.repo;

import java.util.List;


import com.tentochu.tenphammem.demo.entities.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandsRepo extends JpaRepository<Brands,Long> {
    List<Brands> findByBrandName(String brandName);
    boolean existsById(Long id);
}