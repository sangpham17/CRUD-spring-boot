package com.tentochu.tenphammem.demo.service;

import java.util.List;
import java.util.Optional;


import com.tentochu.tenphammem.demo.entities.Brands;
import com.tentochu.tenphammem.demo.repo.BrandsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BrandsService {


    public final BrandsRepo brandsRepo;

    public Optional<Brands> findById(Long id) {
        return brandsRepo.findById(id);
    }

    public List<Brands> findAll() {
        return brandsRepo.findAll();
    }

    public Brands save(Brands brand) {
        return brandsRepo.save(brand);
    }

    public void delete(Long id) {
        brandsRepo.deleteById(id);
    }

    public List<Brands> findByBrandName(String brandName){
        return brandsRepo.findByBrandName(brandName);
    }

    public boolean existsById(Long id){
        return brandsRepo.existsById(id);
    }
}
