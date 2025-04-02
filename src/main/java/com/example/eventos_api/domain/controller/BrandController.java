package com.example.eventos_api.domain.controller;
import com.example.eventos_api.domain.brand.Brand;
import com.example.eventos_api.domain.brand.BrandDTO;
import com.example.eventos_api.domain.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO createBrand(@RequestBody BrandDTO dto) {
        Brand brand = new Brand();
        brand.setName(dto.name());
        Brand saved = brandService.createBrand(brand);
        return new BrandDTO(saved.getName());
    }

    @GetMapping
    public List<Brand> listBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable UUID id) {
        return brandService.getBrandById(id);
    }

    @PutMapping("/{id}")
    public BrandDTO updateBrand(@PathVariable UUID id, @RequestBody BrandDTO dto) {
        Brand brand = new Brand();
        brand.setName(dto.name());
        Brand updated = brandService.updateBrand(id, brand);
        return new BrandDTO(updated.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
    }
}
