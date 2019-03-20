package zohar.crawler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zohar.crawler.dto.ProductReviewDto;
import zohar.crawler.model.Product;
import zohar.crawler.model.ProductReview;
import zohar.crawler.repos.ProductRepos;
import zohar.crawler.repos.ProductReviewRepos;

@Service
public class ProductServiceIml implements ProductService {

    @Autowired
    ProductRepos productRepos;

    @Autowired
    ProductReviewRepos productReviewRepos;

    @Override
    public Product saveProduct(ProductReviewDto productReviewDto) {
        Product product = new Product();
        product.setProductName(productReviewDto.getProductName());
        ProductReview productReview = new ProductReview();
        productReview.setTimeReviews(productReviewDto.getTimeReviews());
        productReview.setUserName(productReviewDto.getUserName());
        productReview.setStar(productReviewDto.getStar());
        productReview.setContent(productReviewDto.getContent());
        productReview.setPeopleLiked(productReviewDto.getPeopleLiked());
        productReview.setProduct(product);
        
        productRepos.save(product);
        productReviewRepos.save(productReview);
        return product;
    }
    
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepos.getAllProducts();
        return products;
    }
    
    @Override
    public List<String> getAllProductNames() {
        return productRepos.getAllProductNames();
    }

}
