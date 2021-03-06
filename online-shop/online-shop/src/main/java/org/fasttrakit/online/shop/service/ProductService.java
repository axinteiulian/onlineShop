package org.fasttrakit.online.shop.service;

import org.fasttrakit.online.shop.domain.Product;
import org.fasttrakit.online.shop.exception.ResourceNotFoundException;
import org.fasttrakit.online.shop.persistance.ProductRepository;
import org.fasttrakit.online.shop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


// inversion of control (IoC)
    private final ProductRepository productRepository;

  //dependancy injection ( from IoC container)
@Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

     public Product createProduct(SaveProductRequest request)  {

        LOGGER.info("Creating product {}", request);
        Product product= new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

   return productRepository.save(product);
}

    public Product getProduct(long id) {
    LOGGER.info("Retrieving product {}", id);

    //optional usage explained
//        Optional<Product> productOptional = productRepository.findById(id);
//
//        if(productOptional.isPresent()){
//            return productOptional.get();
//        }else{
//            throw new ResourceNotFoundException("Product" + id + "not found.");
//        }

        // lamda expressions
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product" + id + "not found."));


        return null;
    }
}


