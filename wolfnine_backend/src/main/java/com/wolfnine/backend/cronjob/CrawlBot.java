package com.wolfnine.backend.cronjob;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.wolfnine.backend.constant.CrawlConstant;
import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.entityEnum.IsArray;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import com.wolfnine.backend.entity.entityEnum.SelectorType;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableAsync
@Component
@EnableScheduling
public class CrawlBot {
    private static WebDriver driver;
    @Autowired
    private CrawlCategoryService crawlCategoryService;
    @Autowired
    private ProductService productService;

    public CrawlBot() {
        WebDriverManager.chromedriver().setup();
    }

//    @Async
//    @Scheduled(fixedRate = 1000 * 60)
    public void crawlList() throws InterruptedException {
        this.driver = new ChromeDriver();
        System.out.println("Bot running ...");
        List<CrawlCategory> crawlCategories = crawlCategoryService.findAll();
        List<Product> products = new ArrayList<>();
        for (CrawlCategory category : crawlCategories) {
            System.out.println("Start crawling ...");
            driver.get(category.getLink());
            List<WebElement> elements = driver.findElements(By.cssSelector(category.getCrawlConfig().getSelectorList()));
            for (WebElement element: elements) {
                //Bóc tách dữ liệu của từng item và lưu vào trong database
                try {
                    JsonParser jsonParser = new JsonParser();
                    JsonArray attributeArray = jsonParser.parse(category.getCrawlConfig().getSelectors()).getAsJsonArray();
                    JsonObject productAttributes = new JsonObject();
                    String productLink = "";
                    for(JsonElement attributeJson : attributeArray) {
                        JsonObject attribute = attributeJson.getAsJsonObject();
                        WebElement elm = element.findElement(By.cssSelector(attribute.get(CrawlConstant.CONFIG_SELECTOR_VALUE).getAsString()));
                        SelectorType selectorType = SelectorType.of(attribute.get(CrawlConstant.CONFIG_SELECTOR_TYPE).getAsInt());
                        String elmValue = selectorType == SelectorType.GET_TEXT
                                ? elm.getText()
                                : selectorType == SelectorType.GET_ATTRIBUTE
                                ? elm.getAttribute(attribute.get(CrawlConstant.CONFIG_SELECTOR_ATTRIBUTE).getAsString())
                                : "";
                        if(attribute.get(CrawlConstant.CONFIG_KEY_IS_LINK).getAsInt() == CrawlConstant.CONFIG_KEY_IS_LINK_ACTIVE) {
                            productLink = elmValue;
                        }
                        productAttributes.addProperty(attribute.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString(), elmValue);
                    }
                    Product product = Product.builder()
                            .crawlCategoryId(category.getId())
                            .categoryId(category.getCategoryId())
                            .link(productLink)
                            .attributes(productAttributes.toString())
                            .userId(category.getUserId())
                            .build();
                    products.add(product);
                }catch (Exception e) {
                    continue;
                }

//                try {
//                    WebElement elmTitle = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorTitle()));
//                    System.out.println("Title " + elmTitle.getText());
//                    WebElement elmThumbnail = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorThumbnail()));
//                    WebElement elmPrice = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorPrice()));
//                    WebElement elmDiscountPrice = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorDiscountPrice()));
//                    WebElement elmLink = element.findElement(By.cssSelector(category.getCrawlConfig().getSelectorLink()));
//                    JsonObject attributes = new JsonObject();
//                    attributes.addProperty("title", elmTitle.getText());
//                    attributes.addProperty("price", elmPrice.getText());
//                    attributes.addProperty("thumbnail", elmThumbnail.getAttribute("src"));
//                    attributes.addProperty("discountPrice", elmDiscountPrice.getText());
//                    attributes.addProperty("link", elmLink.getAttribute("href"));
//                    Product newProduct = Product.builder()
//                            .categoryId(category.getCategoryId())
//                            .crawlCategoryId(category.getId())
//                            .userId(category.getUserId())
//                            .attributes(attributes.toString()) // dạng json object
//                            .build();
//                    products.add(newProduct);
//                }catch (Exception e) {
//                  continue;
//                }
            }
        }
        productService.saveAll(products);
        System.out.println("End running ...");
        driver.quit();
        Thread.sleep(2000);
    }

//    @Async
//    @Scheduled(fixedRate = 1000 * 60)
    public void crawlDetails() throws InterruptedException{
        this.driver = new ChromeDriver();
        System.out.println("Begin crawl details ...");
        List<Product> products = productService.findAllByStatus(ProductStatus.NOT_CRAWL);
        System.out.println("Size of products " + products.size());
        for(Product product : products) {
            System.out.println("Start crawl product " + product.getId());
            driver.get(product.getLink());
            JsonParser parser = new JsonParser();
            JsonArray attributeArray = parser.parse(product.getCrawlCategory().getCrawlConfig().getSelectorDetails()).getAsJsonArray();
            JsonObject productAttributes = parser.parse(product.getAttributes()).getAsJsonObject();
            System.out.println("Size of selector " + attributeArray.size());
            for(JsonElement attrElm : attributeArray) {
                JsonObject attribute = attrElm.getAsJsonObject();
                IsArray isArray = IsArray.of(attribute.get(CrawlConstant.CONFIG_KEY_IS_ARRAY).getAsInt());
                if(isArray == IsArray.ACTIVE) {
                    System.out.println("Hello88888888");
                    List<WebElement> elements = driver.findElements(By.cssSelector(attribute.get(CrawlConstant.CONFIG_SELECTOR_VALUE).getAsString()));
                    JsonArray elmValueArray = new JsonArray();
                    for(WebElement element : elements) {
                        SelectorType selectorType = SelectorType.of(attribute.get(CrawlConstant.CONFIG_SELECTOR_TYPE).getAsInt());
                        String elmValue = selectorType == SelectorType.GET_TEXT
                                ? element.getText()
                                : selectorType == SelectorType.GET_ATTRIBUTE
                                ? element.getAttribute(attribute.get(CrawlConstant.CONFIG_SELECTOR_ATTRIBUTE).getAsString())
                                : "";
                        elmValueArray.add(elmValue);
                    }
                    productAttributes.add(attribute.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString(),elmValueArray);
                }else {
                    WebElement elm = driver.findElement(By.cssSelector(attribute.get(CrawlConstant.CONFIG_SELECTOR_VALUE).getAsString()));
                    SelectorType selectorType = SelectorType.of(attribute.get(CrawlConstant.CONFIG_SELECTOR_TYPE).getAsInt());
                    String elmValue = selectorType == SelectorType.GET_TEXT
                            ? elm.getText()
                            : selectorType == SelectorType.GET_ATTRIBUTE
                            ? elm.getAttribute(attribute.get(CrawlConstant.CONFIG_SELECTOR_ATTRIBUTE).getAsString())
                            : "";
                    productAttributes.addProperty(attribute.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString(), elmValue);
                }
            }
//            product.setStatus(ProductStatus.CRAWLED);
            product.setAttributes(productAttributes.toString());
            productService.update(product.getId(), product);
        }
        System.out.println("End crawl details ...");
        Thread.sleep(2000);
        driver.quit();
    }
}
