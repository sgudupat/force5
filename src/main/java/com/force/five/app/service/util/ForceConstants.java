package com.force.five.app.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by PSC Global on 7/28/2016.
 */
public class ForceConstants {

    public  static BigDecimal TOTAL_DAYS = new BigDecimal("30");
    public  static BigDecimal PF_CAL = new BigDecimal("12").divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
   public static BigDecimal ESIC = new BigDecimal("1.75").divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);

}
