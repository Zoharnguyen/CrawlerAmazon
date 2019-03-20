package zohar.crawler.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zohar.crawler.model.Product;

@Repository
public interface ProductRepos extends CrudRepository<Product, Integer> {

    @Query("select product from Product product")
    List<Product> getAllProducts();
    
    @Query("select product.productName from Product product")
    List<String> getAllProductNames();
    
}
