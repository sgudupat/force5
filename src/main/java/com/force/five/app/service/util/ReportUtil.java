package com.force.five.app.service.util;

import com.force.five.app.domain.Client;
import com.force.five.app.domain.PaySheets;

import java.math.BigDecimal;

public class ReportUtil {

    public static BigDecimal getTotalDedu(Client client, PaySheets ps) {
        BigDecimal pf = BigDecimal.ZERO;
        BigDecimal esic = BigDecimal.ZERO;
        if (client.getPf()) {
            pf = ps.getPF();
        }
        if (client.getEsic()) {
            esic = ps.getESIC();
        }
        return pf.add(esic);
    }

    public static BigDecimal getNetSalary(Client client, PaySheets ps) {
        return ps.getGrossWages().subtract(getTotalDedu(client, ps));
    }
}
