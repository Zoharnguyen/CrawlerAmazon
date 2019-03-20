package zohar.crawler.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import zohar.crawler.dto.ProductReviewDto;
import zohar.crawler.service.HelperService;
import zohar.crawler.service.ProductService;

@Service
public class MyCrawler extends WebCrawler {

    @Autowired
    ProductService productService;

    HelperService helperService;

    public Logger LOGGER = Logger.getLogger(MyCrawler.class.getName());

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");

    private final static Pattern filterGetLinkProductDetails = Pattern
            .compile("https:\\/\\/www\\.amazon\\.com\\/(.)+\\/dp\\/(.)+\\/ref=");

    private final static Pattern filterGetLinkProductReviews = Pattern
            .compile("https:\\/\\/www\\.amazon\\.com\\/(.)+\\/product\\-reviews\\/(.)+");
    private List<String> productNames = Arrays.asList(new String[] { "foo" });

    Set<String> linkProductDetails = new HashSet<String>();
    int countSV = 0;
    int countV = 0;
    int countElementProductNames = 0;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

//      LOGGER.log(Level.INFO, "URL is: " + url.getURL());
//      LOGGER.log(Level.INFO, "Page's url is: " + referringPage.getWebURL().getURL());

        String pageUrl = referringPage.getWebURL().getURL().toLowerCase();
        String href = url.getURL().toLowerCase();
        boolean result = (filterGetLinkProductDetails.matcher(href).find()
                && !filterGetLinkProductDetails.matcher(pageUrl).find())
                || filterGetLinkProductReviews.matcher(href).find();

        if (filterGetLinkProductDetails.matcher(href).find() && filterGetLinkProductDetails.matcher(pageUrl).find()) {
            result = !getProductName(href).equals(getProductName(pageUrl));
        }

        return !FILTERS.matcher(href).matches() && href.startsWith("https://www.amazon.com/") && result;
    }

    @Override
    public void visit(Page page) {
        LOGGER.log(Level.INFO, "Page visiting: " + page.getWebURL().getURL());
        countV++;
        if (countElementProductNames == 0) {
//            productNames = productService.getAllProductNames();            
            countElementProductNames++;
        }
        System.out.println("Total number page Visiting : " + countV);
        String url = page.getWebURL().getURL();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            if (filterGetLinkProductDetails.matcher(url).find()) {
                try {
                    Document document = Jsoup.connect(url).get();
                    Element element = document.getElementById("dp-summary-see-all-reviews");
                    String href = "https://www.amazon.com" + element.attr("href");
                    System.out.println("Url product reviews is: " + href);
                    this.myController.addSeed(href);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (filterGetLinkProductReviews.matcher(url).find()) {

                String productName = helperService.getProductName(url);
//                if (!helperService.checkExistProductReviewIntoDb(productName, productNames)) {
//                    productNames.add(productName);

                Document document = Jsoup.parse(html);
                Elements elements = document.select(
                        "div.a-section.a-spacing-none.review-views.celwidget > div.a-section.review.aok-relative");
                for (Element element : elements) {
                    ProductReviewDto productReviewDto = new ProductReviewDto();
                    String nameReviewer = element
                            .select("div.a-section.celwidget > div > a > div > span.a-profile-name").text();
                    String starsReviewereValuation = element
                            .select("div.a-section.celwidget > div > a > i > span.a-icon-alt").text();
                    String titleReview = element.select("div.a-section.celwidget > div > a > span").text();
                    String dateReviewerPoscomment = element
                            .select("div> div > span.a-size-base.a-color-secondary.review-date").text();
                    String contentReviewerComment = element
                            .select("div.a-section.celwidget > div.a-row.a-spacing-small.review-data > span > span")
                            .text();
                    String numberPersonseeHelpful = element
                            .select("div.a-section.celwidget > div > div > span > div > span").text();                

                    productReviewDto.setContent(contentReviewerComment);
                    productReviewDto.setPeopleLiked(numberPersonseeHelpful);
                    productReviewDto.setStar(starsReviewereValuation);
                    productReviewDto.setTimeReviews(dateReviewerPoscomment);
                    productReviewDto.setUserName(nameReviewer);
                    productReviewDto.setProductName(productName);
                    
//                    LOGGER.log(Level.INFO, "Check null of productService: " + productService.toString());   
                    
                    productService.saveProduct(productReviewDto);                                           

                // Write data into file.txt

//                BufferedWriter writer;
//                try {
//                    writer = new BufferedWriter(
//                            new FileWriter("C:/Users/PC/eclipse-workspace/CrawlerBasic/content.txt", true));
//                    String content = "\r\nPage'url is: " + url + "\r\n" + nameReviewer + "\r\n"
//                            + starsReviewereValuation + "\r\n" + titleReview + "\r\n" + dateReviewerPoscomment + "\r\n"
//                            + contentReviewerComment + "\r\n" + numberPersonseeHelpful + "\r\n";
//                    writer.append(content);
//                    writer.close();
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//
                    
            }                    
        }
    }

    }

    // Get product'Name
    public static String getProductName(String url) {
        int indexStart = url.indexOf("com/") + 4;
        System.out.println("IndexStart is: " + indexStart);
        int indexEnd = url.indexOf("/", indexStart);
        System.out.println("IndexEnd is: " + indexEnd);
        String productName = url.substring(indexStart, indexEnd);
        return productName;
    }

}
