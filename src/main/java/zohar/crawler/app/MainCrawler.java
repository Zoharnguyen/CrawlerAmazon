package zohar.crawler.app;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.CrawlController.WebCrawlerFactory;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MainCrawler {       
    
    public static void main(String[] agrs) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numThread = 1;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(1);
        config.setMaxPagesToFetch(1);
        config.setUserAgentString(
                "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405");

        // Khai bao controller

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        int i = 1;
//      for(i = 1; i <= 10; i++) {
//        controller.addSeed("https://www.amazon.com/s?k=" + productName + "&page=" + 1);
//      }               

        controller.addSeed(
                "https://www.amazon.com/Acer-Touchscreen-Convertible-Quad-Core-Professional/product-reviews/B07LDT92L7/ref=cm_cr_dp_d_show_all_top?ie=UTF8&reviewerType=all_reviews");               
        
        MyCrawler myCrawler;
        
        CrawlController.WebCrawlerFactory<MyCrawler> factory = (WebCrawlerFactory<MyCrawler>) new MyCrawler();

        controller.start(factory, numThread);

    }
    
}
