package zohar.crawler.service;

import java.util.List;

public class HelperService {
    
    public static String getProductName(String url) {
        int indexStart = url.indexOf("com/") + 4;
        System.out.println("IndexStart is: " + indexStart);
        int indexEnd = url.indexOf("/", indexStart);
        System.out.println("IndexEnd is: " + indexEnd);
        String productName = url.substring(indexStart, indexEnd);
        return productName;
    }
    
    public boolean checkExistProductReviewIntoDb(String productName, List<String> productNames) {
        if(!productNames.isEmpty()) {
            return productNames.contains(productName);
        }
        return false;
    }
    
}
