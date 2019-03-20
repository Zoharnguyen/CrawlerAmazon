package zohar.crawler.dto;

public class ProductReviewDto {

    private String productName;
    private String timeReviews;
    private String userName;
    private String star;
    private String content;
    private String peopleLiked;

    public ProductReviewDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTimeReviews() {
        return timeReviews;
    }

    public void setTimeReviews(String timeReviews) {
        this.timeReviews = timeReviews;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPeopleLiked() {
        return peopleLiked;
    }

    public void setPeopleLiked(String peopleLiked) {
        this.peopleLiked = peopleLiked;
    }

}
