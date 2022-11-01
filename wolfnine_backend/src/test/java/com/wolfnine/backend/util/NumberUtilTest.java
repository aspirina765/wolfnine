package com.wolfnine.backend.util;

import com.wolfnine.backend.WolfnineBackendApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
class NumberUtilTest {

    @Test
    void removeSymbolCurrency() {
        double number = NumberUtil.removeSymbolCurrency("￦329,000");
        System.out.println(number);
    }
}
