package zohar.crawler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import zohar.crawler.dto.ProductReviewDto;
import zohar.crawler.model.Product;

public interface ProductService {

    Product saveProduct(ProductReviewDto productReviewDto);
    List<Product> getAllProducts();
    List<String> getAllProductNames();
    
}
