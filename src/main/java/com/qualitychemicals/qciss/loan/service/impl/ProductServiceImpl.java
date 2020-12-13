package com.qualitychemicals.qciss.loan.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
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
        boolean bool=checkProduct(productDto.getName(), productDto.getProductNumber());
        if(bool) {

            logger.info("converting ...");
            Product product = productConverter.dtoToEntity(productDto);
            logger.info("saving ...");
            return productDao.save(product);
        }else{throw new InvalidValuesException("Product already exists");}
    }
    private boolean checkProduct(String name, String number){
        boolean bool=productDao.existsByName(name);
        if(bool){ throw new InvalidValuesException("Product name already used");
        }else{
          boolean bool1=productDao.existsByProductNumber(number);
            if(bool1) {
                throw new InvalidValuesException("Product number already used");
            }else{
                return true;
            }
        }
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
