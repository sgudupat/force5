package com.force.five.app.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ForceConstants {

    public static BigDecimal TOTAL_DAYS = new BigDecimal("30");
    public static BigDecimal PF_CAL = new BigDecimal("12").divide(new BigDecimal("100"), 4, RoundingMode.HALF_EVEN);
    public static BigDecimal ESIC = new BigDecimal("1.75").divide(new BigDecimal("100"), 4, RoundingMode.HALF_EVEN);
    public static BigDecimal SERVICE_TAX = new BigDecimal("14.5").divide(new BigDecimal("100"), 4, RoundingMode.HALF_EVEN);

}
