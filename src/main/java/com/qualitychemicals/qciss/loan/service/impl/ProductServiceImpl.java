package com.qualitychemicals.qciss.loan.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.converter.ProductConverter;
import com.qualitychemicals.qciss.loan.dao.ProductDao;
import com.qualitychemicals.qciss.loan.dto.ProductDto;
import com.qualitychemicals.qciss.loan.model.Product;
import com.qualitychemicals.qciss.loan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired ProductConverter productConverter;
    @Autowired ProductDao productDao;
    @Override
    public Product addProduct(ProductDto productDto) {
        Product product=productConverter.dtoToEntity(productDto);
        return productDao.save(product);
    }

    @Override
    public Product getProduct(int id) {
        return productDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such Product ID: "+id));
    }

    @Override
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @Override
    public Product updateProduct(ProductDto productDto, int id) {
        Product product=getProduct(id);
        product.setMinAmount(productDto.getMinAmount());
        product.setMaxAmount(productDto.getMaxAmount());
        product.setDescription(productDto.getDescription());
        product.setInterest(productDto.getInterest());
        product.setMaxDuration(productDto.getMaxDuration());
        product.setMinDuration(productDto.getMinDuration());
        product.setName(productDto.getName());
        product.setPenalty(productDto.getPenalty());
        return productDao.save(product);
    }
}
