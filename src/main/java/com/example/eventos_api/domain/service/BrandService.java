package com.example.eventos_api.domain.service;

import com.example.eventos_api.domain.brand.Brand;
import com.example.eventos_api.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(UUID id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
    }

    public Brand updateBrand(UUID id, Brand brand) {
        Brand existingBrand = getBrandById(id);
        existingBrand.setName(brand.getName());
        return brandRepository.save(existingBrand);
    }

    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }
}
