package com.example.billingservie.web;

import com.example.billingservie.entities.Bill;
import com.example.billingservie.repository.BillRepository;
import com.example.billingservie.repository.ProductItemRepository;
import com.example.billingservie.service.CustomerRestClient;
import com.example.billingservie.service.ProductRestClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BillRestController {
    private BillRepository billRepository;

    private ProductItemRepository productItemRepository;

    private CustomerRestClient customerRestClient;

    private ProductRestClient productRestClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping("/fullBill/{id}")
    public Bill bill(@PathVariable Long id, @RequestHeader("loggedUser") String username) {
        System.out.println("?????????????????????");
        System.out.println(username);
        System.out.println("?????????????????????");
        Optional<Bill> isbill=billRepository.findById(id);
        if(!isbill.isPresent()){
            throw new RuntimeException("bill not existe");
        }
        Bill bill=isbill.get();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(productItem -> {
            productItem.setProduct(productRestClient.findProductById(productItem.getId()));
        });

        return bill;
    }
}
