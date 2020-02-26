package org.fasttrakit.online.shop;

import org.fasttrakit.online.shop.domain.Product;
import org.fasttrakit.online.shop.service.ProductService;
import org.fasttrakit.online.shop.transfer.SaveProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ProductServiceIntegrationTests {


  //field injection (injection dependicies from IoC annotating the field itself )
    // field = instance variables

    @Autowired
    private ProductService productService;

   @Test
   void createProduct_whenValidRequest_thenProductIsCreated(){
       SaveProductRequest request = new SaveProductRequest();
       request.setName("Phone");
       request.setQuantity(100);
       request.setPrice(300.5);
       Product product = productService.createProduct(request);


       assertThat(product, notNullValue());
       assertThat(product.getId(), greaterThan(0L));
       assertThat(product.getName(), is(request.getName()));
       assertThat(product.getPrice(), is(request.getPrice()));
       assertThat(product.getQuantity(), is(request.getQuantity()));

    }
    @Test
    void createProduct_whenMissingName_thenExceptionIsThrown(){
       SaveProductRequest request = new SaveProductRequest();
       request.setQuantity(1);
       request.setPrice(100.0);

        try {
            productService.createProduct(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type.", e instanceof TransactionSystemException);

        }


    }

}
