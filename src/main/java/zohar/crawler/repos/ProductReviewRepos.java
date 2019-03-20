package zohar.crawler.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zohar.crawler.model.ProductReview;

@Repository
public interface ProductReviewRepos extends CrudRepository<ProductReview, Integer> {

}
