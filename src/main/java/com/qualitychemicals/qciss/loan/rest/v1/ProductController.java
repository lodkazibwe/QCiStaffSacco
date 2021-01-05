package com.qualitychemicals.qciss.loan.rest.v1;

import com.qualitychemicals.qciss.loan.converter.ProductConverter;
import com.qualitychemicals.qciss.loan.dto.ProductDto;
import com.qualitychemicals.qciss.loan.model.Product;
import com.qualitychemicals.qciss.loan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("/loan/product")
public class ProductController {
    @Autowired ProductService productService;
    @Autowired ProductConverter productConverter;

    @PostMapping("/admin/add")
    public ResponseEntity<ProductDto> addLoanProduct(@Valid @RequestBody ProductDto productDto){
        Product product=productService.addProduct(productDto);
        return new ResponseEntity<>(productConverter.entityToDto(product), HttpStatus.OK);

    }

    @PostMapping("/admin/update/{id}")
    public ResponseEntity<ProductDto> updateLoanProduct(@Valid @RequestBody ProductDto productDto, @PathVariable int id){
        Product product=productService.updateProduct(productDto, id);
        return new ResponseEntity<>(productConverter.entityToDto(product), HttpStatus.OK);

    }
    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int productId){
        Product product=productService.getProduct(productId);
        return new ResponseEntity<>(productConverter.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<Product> product=productService.getAll();
        return new ResponseEntity<>(productConverter.entityToDto(product), HttpStatus.OK);
    }

}
