package com.qualitychemicals.qciss.loan.rest.v1;

import com.qualitychemicals.qciss.loan.converter.ProductConverter;
import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.dto.ProductDto;
import com.qualitychemicals.qciss.loan.model.Product;
import com.qualitychemicals.qciss.loan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/loan/product")
public class ProductController {
    @Autowired ProductService productService;
    @Autowired ProductConverter productConverter;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addLoanProduct(@Valid @RequestBody ProductDto productDto){
        Product product=productService.addProduct(productDto);
        return new ResponseEntity<>(productConverter.entityToDto(product), HttpStatus.OK);

    }

}
