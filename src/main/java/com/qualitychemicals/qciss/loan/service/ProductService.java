package com.qualitychemicals.qciss.loan.service;

import com.qualitychemicals.qciss.loan.dto.ProductDto;
import com.qualitychemicals.qciss.loan.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    Product getProduct(int id);
    List<Product> getAll();
    Product updateProduct(ProductDto productDto, int id);


}
