package com.qualitychemicals.qciss.loan.converter;

import com.qualitychemicals.qciss.loan.dto.ProductDto;
import com.qualitychemicals.qciss.loan.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {
    public ProductDto entityToDto(Product product){
        ProductDto productDto=new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setInterest(product.getInterest());
        productDto.setMaxAmount(product.getMaxAmount());
        productDto.setMaxDuration(product.getMaxDuration());
        productDto.setMinAmount(product.getMinAmount());
        productDto.setMinDuration(product.getMinDuration());
        productDto.setName(product.getName());
        productDto.setPenalty(product.getPenalty());
        return productDto;

    }
    public Product dtoToEntity(ProductDto productDto){
        Product product=new Product();
        product.setDescription(productDto.getDescription());
        product.setInterest(productDto.getInterest());
        product.setMaxAmount(productDto.getMaxAmount());
        product.setMaxDuration(productDto.getMaxDuration());
        product.setMinAmount(productDto.getMinAmount());
        product.setMinDuration(productDto.getMinDuration());
        product.setPenalty(productDto.getPenalty());
        product.setName(productDto.getName());
        return product;
    }
    public List<ProductDto> entityToDto(List<Product> products){
        return products.stream().map(this::entityToDto).collect(Collectors.toList());

    }
}
