package com.wolfnine.backend.util;

import java.text.NumberFormat;

public class NumberUtil {
    public static double removeSymbolCurrency(String moneyString) {
        String numberFormat = moneyString.replaceAll("[^0-9]", "").replace(",", "");
        return Double.parseDouble(numberFormat);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
