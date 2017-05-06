package ro.fortech.application.bidstore.backend.util;

import java.text.DecimalFormat;

public class Formatter {

    static final DecimalFormat FORMAT = new DecimalFormat("#.00");

    public static Double formatPrice(Double price) {
        return Double.parseDouble(FORMAT.format(price));
    }
}
