package com.example.billingservie.service;

import com.example.billingservie.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping(path = "/products/{id}")
    Product findProductById(@PathVariable Long id);

    @GetMapping(path = "/products") //la service product il retourne un json qui contienne la paginiation (le json start with embeded {...}) donc l'objet dans java qui peut contenire cette model est la PagedMode !
    PagedModel<Product> allProduct();
}
