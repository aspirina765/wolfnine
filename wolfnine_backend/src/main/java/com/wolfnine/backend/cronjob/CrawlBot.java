package com.wolfnine.backend.cronjob;

import com.google.gson.JsonObject;
import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.service.crawlCategory.CrawlCategoryService;
import com.wolfnine.backend.service.product.ProductService;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
@Configuration
@EnableScheduling
public class CrawlBot {
    private static WebDriver driver;
    @Autowired
    private CrawlCategoryService categoryService;
    @Autowired
    private ProductService productService;

    public CrawlBot() {
        WebDriverManager.chromedriver().setup();
    }

    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void crawling() throws InterruptedException {
        this.driver = new ChromeDriver();
        System.out.println("Bot running ...");
        List<CrawlCategory> crawlCategories = categoryService.findByUserId(1);
        List<Product> products = new ArrayList<>();
        for (CrawlCategory category : crawlCategories) {
            System.out.println("Start crawling ...");
            driver.get(category.getLink());
            List<WebElement> elements = driver.findElements(By.cssSelector(category.getCrawlConfig().getSelectorList()));
            for (WebElement element: elements) {
                //Bóc tách dữ liệu của từng item và lưu vào trong database
                try {
                    WebElement elmTitle = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorTitle()));
                    System.out.println("Title " + elmTitle.getText());
                    WebElement elmThumbnail = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorThumbnail()));
                    WebElement elmPrice = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorPrice()));
                    WebElement elmDiscountPrice = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorDiscountPrice()));
                    WebElement elmLink = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorLink()));
                    JsonObject attributes = new JsonObject();
                    attributes.addProperty("title", elmTitle.getText());
                    attributes.addProperty("price", elmPrice.getText());
                    attributes.addProperty("thumbnail", elmThumbnail.getAttribute("src"));
                    attributes.addProperty("discountPrice", elmDiscountPrice.getText());
                    attributes.addProperty("link", elmLink.getAttribute("href"));
                    Product newProduct = Product.builder()
                            .categoryId(1)
                            .crawlCategoryId(category.getId())
                            .userId(1)
                            .attributes(attributes.toString()) // dạng json object
                            .build();
                    products.add(newProduct);
                }catch (Exception e) {
                  continue;
                }
            }
        }
        productService.saveAll(products);
        System.out.println("End running ...");
        Thread.sleep(2000);
        driver.quit();
    }
}
