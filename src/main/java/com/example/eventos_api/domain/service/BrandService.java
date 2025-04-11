package com.example.eventos_api.domain.service;
import com.example.eventos_api.domain.brand.Brand;
import com.example.eventos_api.domain.brand.BrandResponseDTO;
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

    public BrandResponseDTO getBrandWithItems(UUID id) {
        Brand brand = getBrandById(id);
        return new BrandResponseDTO(brand);
    }

    public List<BrandResponseDTO> getAllBrandsWithItems() {
        return brandRepository.findAll().stream()
                .map(BrandResponseDTO::new)
                .toList();
    }

    public List<BrandResponseDTO> getFilteredBrands(String name, List<UUID> ids) {
        List<Brand> allBrands = brandRepository.findAll();

        return allBrands.stream()
                .filter(brand -> {
                    boolean matchName = name == null || brand.getName().toLowerCase().contains(name.toLowerCase());
                    boolean matchId = ids == null || ids.contains(brand.getId());
                    return matchName && matchId;
                })
                .map(BrandResponseDTO::new)
                .toList();
    }

    public Brand updateBrand(UUID id, Brand brand) {
        Brand existingBrand = getBrandById(id);
        existingBrand.setName(brand.getName());
        existingBrand.setDescription(brand.getDescription());
        return brandRepository.save(existingBrand);
    }

    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }
}
