package com.qualitychemicals.qciss.loan.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.converter.ProductConverter;
import com.qualitychemicals.qciss.loan.dao.ProductDao;
import com.qualitychemicals.qciss.loan.dto.ProductDto;
import com.qualitychemicals.qciss.loan.model.Product;
import com.qualitychemicals.qciss.loan.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired ProductConverter productConverter;
    @Autowired ProductDao productDao;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public Product addProduct(ProductDto productDto) {
        logger.info("converting ...");
        Product product=productConverter.dtoToEntity(productDto);
        logger.info("saving ...");
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
        logger.info("getting product ...");
        Product product=getProduct(id);
        logger.info("updating product ...");
        Product updatedProduct=productConverter.dtoToEntity(productDto);
        updatedProduct.setId(product.getId());
        logger.info("saving product ...");
        return productDao.save(updatedProduct);
    }
}
