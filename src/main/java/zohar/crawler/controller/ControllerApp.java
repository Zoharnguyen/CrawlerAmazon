package zohar.crawler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zohar.crawler.app.ControllerCrawler;
import zohar.crawler.model.Product;
import zohar.crawler.service.ProductService;

@RestController
@RequestMapping("/amazon")
public class ControllerApp {
    
    @Autowired
    ControllerCrawler controllerCrawler;

    @Autowired
    ProductService productService;
    
    @GetMapping("/reviews")
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }
    
    @GetMapping("/crawl")
    public ResponseEntity<Object> crawlData() {
        try {
            controllerCrawler.excute("laptop");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
