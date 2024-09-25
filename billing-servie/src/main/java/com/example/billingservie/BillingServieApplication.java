package com.example.billingservie;

import com.example.billingservie.entities.Bill;
import com.example.billingservie.entities.ProductItem;
import com.example.billingservie.model.Customer;
import com.example.billingservie.model.Product;
import com.example.billingservie.repository.BillRepository;
import com.example.billingservie.repository.ProductItemRepository;
import com.example.billingservie.service.CustomerRestClient;
import com.example.billingservie.service.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServieApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServieApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient){
        return args -> {
            Collection<Product> products = productRestClient.allProduct().getContent();

            Long customerId=1L;

            Customer customer=customerRestClient.findCustomerById(customerId);

            if(customer==null) throw new RuntimeException("Customer not found");

            Bill bill =new Bill();

            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);

            Bill saveBill = billRepository.save(bill);

            products.forEach(product -> {
                ProductItem productItem=new ProductItem();
                productItem.setBill(saveBill);
                productItem.setQuantity(1+new Random().nextInt(10));
                productItem.setPrice(product.getPrice());
                productItem.setDiscount(Math.random());
                productItem.setProductId(product.getId());
                productItemRepository.save(productItem);
            });




        };
    }

}