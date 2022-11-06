package com.wolfnine.backend.cronjob;

import com.wolfnine.backend.WolfnineBackendApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class CrawlBotTest {
    @Autowired
    CrawlBot crawlBot;

    @BeforeEach
    void setUp() {
    }

    @Test
    void crawlList() throws InterruptedException {
        crawlBot.crawlList();
    }

    @Test
    void crawlDetails() throws InterruptedException {
        crawlBot.crawlDetails();
    }

    @Test
    void getProxies() {
        List<String> proxies = crawlBot.getProxies();
        assertNotEquals(0, proxies.size());
    }
}
