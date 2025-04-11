package com.example.eventos_api.domain.item;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemSimpleDTO {
    private UUID id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private List<String> imageUrls;
    private UUID categoryId;
    private UUID brandId;

    public ItemSimpleDTO(Item item) {
        this.id = item.getId();
        this.sku = item.getSku();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.stock = item.getStock();
        this.imageUrls = item.getImageUrls() != null
                ? Arrays.asList(item.getImageUrls())
                : List.of();
        this.categoryId = item.getCategory() != null ? item.getCategory().getId() : null;
        this.brandId = item.getBrand() != null ? item.getBrand().getId() : null;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getBrandId() {
        return brandId;
    }
    public void setBrandId(UUID brandId) {
        this.brandId = brandId;
    }
}
