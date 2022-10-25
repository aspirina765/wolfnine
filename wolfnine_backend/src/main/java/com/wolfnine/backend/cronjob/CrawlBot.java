package com.wolfnine.backend.cronjob;

import com.google.gson.*;
import com.wolfnine.backend.constant.CrawlConstant;
import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.entityEnum.*;
import com.wolfnine.backend.service.crawlCategory.CrawlCategoryService;
import com.wolfnine.backend.service.product.ProductService;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
//@Component
//@EnableScheduling
public class CrawlBot {
    private static WebDriver driver;
    private static ChromeOptions options;
    @Autowired
    private CrawlCategoryService crawlCategoryService;
    @Autowired
    private ProductService productService;

    public CrawlBot() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions().setHeadless(false);
        driver = new ChromeDriver();
    }

    @Async
    @Scheduled(fixedRate = 1000 * 5)
    public void crawlList() throws InterruptedException {
        System.out.println("Bot running ...");
        List<CrawlCategory> crawlCategories = crawlCategoryService.findAllByStatus(CrawlCategoryStatus.PENDING);
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
                    JsonArray productAttributes = new JsonArray();
                    String productLink = "";
                    for(JsonElement attributeJson : attributeArray) {
                        JsonObject attribute = attributeJson.getAsJsonObject();
                        WebElement elm = element.findElement(By.cssSelector(attribute.get(CrawlConstant.CONFIG_SELECTOR_VALUE).getAsString()));
                        SelectorType selectorType = SelectorType.of(attribute.get(CrawlConstant.CONFIG_SELECTOR_TYPE).getAsInt());
                        String elmValue = "";
                        switch (selectorType) {
                            case GET_TEXT:
                                elmValue = elm.getText();
                                break;
                            case GET_ATTRIBUTE:
                                elmValue = elm.getAttribute(attribute.get(CrawlConstant.CONFIG_SELECTOR_ATTRIBUTE).getAsString());
                                break;
                            case GET_HTML_CONTENT:
                                elmValue = elm.getAttribute("innerHTML");
                                break;
                            default:
                                break;
                        }
                        if(attribute.get(CrawlConstant.CONFIG_KEY_IS_LINK).getAsInt() == CrawlConstant.CONFIG_KEY_IS_LINK_ACTIVE) {
                            productLink = elmValue;
                        }
                        JsonObject elmValueObject = new JsonObject();
                        CrawlDataType elmValueType = CrawlDataType.of(attribute.get(CrawlConstant.CONFIG_KEY_DATA_TYPE).getAsInt());
                        elmValueObject.addProperty(CrawlConstant.CONFIG_SELECTOR_TYPE, elmValueType.getValue());
                        elmValueObject.addProperty(CrawlConstant.CONFIG_KEY_ITEM_VALUE, elmValue);
                        elmValueObject.addProperty(CrawlConstant.CONFIG_SELECTOR_KEY, attribute.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString());
                        productAttributes.add(elmValueObject);
                    }
                    Product product = Product.builder()
                            .crawlCategoryId(category.getId())
                            .categoryId(category.getCategoryId())
                            .link(productLink)
                            .attributes(productAttributes.toString())
                            .userId(category.getUserId())
                            .status(ProductStatus.PENDING)
                            .build();
                    products.add(product);
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
            category.setStatus(CrawlCategoryStatus.CRAWLED);
            crawlCategoryService.update(category.getId(), category);
        }
        productService.saveAll(products);
        System.out.println("End running ...");
        Thread.sleep(5000);
    }

//    @Async
//    @Scheduled(fixedRate = 1000 * 5)
    public void crawlDetails() throws InterruptedException{
        System.out.println("Begin crawl details ...");
        List<Product> products = productService.findAllByStatus(ProductStatus.PENDING);
        for(Product product : products) {
            System.out.println("Start crawl product " + product.getId());
            driver.get(product.getLink());
            JsonParser parser = new JsonParser();
            JsonArray attributeArray = parser.parse(product.getCrawlCategory().getCrawlConfig().getSelectorDetails()).getAsJsonArray();
            JsonObject productAttributes = parser.parse(product.getAttributes()).getAsJsonObject();
            for(JsonElement attrElm : attributeArray) {
                JsonObject attribute = attrElm.getAsJsonObject();
                IsArray isArray = IsArray.of(attribute.get(CrawlConstant.CONFIG_KEY_IS_ARRAY).getAsInt());
                if(isArray == IsArray.ACTIVE) {
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
                    JsonObject elmValueObject = new JsonObject();
                    CrawlDataType elmValueType = CrawlDataType.of(attribute.get(CrawlConstant.CONFIG_KEY_DATA_TYPE).getAsInt());
                    elmValueObject.addProperty(CrawlConstant.CONFIG_SELECTOR_TYPE, elmValueType.getValue());
                    elmValueObject.addProperty(CrawlConstant.CONFIG_KEY_ITEM_VALUE, elmValueArray.toString());
                    productAttributes.add(attribute.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString(), elmValueObject);
                }else {
                    WebElement elm = driver.findElement(By.cssSelector(attribute.get(CrawlConstant.CONFIG_SELECTOR_VALUE).getAsString()));
                    SelectorType selectorType = SelectorType.of(attribute.get(CrawlConstant.CONFIG_SELECTOR_TYPE).getAsInt());
                    String elmValue = selectorType == SelectorType.GET_TEXT
                            ? elm.getText()
                            : selectorType == SelectorType.GET_ATTRIBUTE
                            ? elm.getAttribute(attribute.get(CrawlConstant.CONFIG_SELECTOR_ATTRIBUTE).getAsString())
                            : "";
                    JsonObject elmValueObject = new JsonObject();
                    CrawlDataType elmValueType = CrawlDataType.of(attribute.get(CrawlConstant.CONFIG_KEY_DATA_TYPE).getAsInt());
                    elmValueObject.addProperty(CrawlConstant.CONFIG_SELECTOR_TYPE, elmValueType.getValue());
                    elmValueObject.addProperty(CrawlConstant.CONFIG_KEY_ITEM_VALUE, elmValue);
                    productAttributes.add(attribute.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString(), elmValueObject);
                }
            }
            product.setStatus(ProductStatus.CRAWLED);
            product.setAttributes(productAttributes.toString());
            productService.update(product.getId(), product);
        }
        System.out.println("End crawl details ...");
        Thread.sleep(2000);
    }
}
