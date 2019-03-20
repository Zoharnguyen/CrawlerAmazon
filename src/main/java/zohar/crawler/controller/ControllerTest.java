package zohar.crawler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zohar.crawler.dto.ProductReviewDto;
import zohar.crawler.service.ProductService;

@RestController
@RequestMapping("/test")
public class ControllerTest {

    @Autowired
    ProductService productService;
    
    @GetMapping
    public List<String> getAllProductNames() {
        return productService.getAllProductNames();
    }
    
    @GetMapping("/save")
    public ResponseEntity<Object> saveProductReviews() {
        ProductReviewDto productReviewDto = new ProductReviewDto();
        productReviewDto.setContent("2");
        productReviewDto.setPeopleLiked("2");
        productReviewDto.setProductName("2");
        productReviewDto.setStar("2");
        productReviewDto.setTimeReviews("2");
        productReviewDto.setUserName("2");
       
        return new ResponseEntity<Object>(productService.saveProduct(productReviewDto), HttpStatus.OK);
    }
    
}
