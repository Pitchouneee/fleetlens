package fr.corentinbringer.fleetlens.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("mathUtils")
public class MathUtils {

    public Double roundToTwoDecimals(Double value) {
        if (value == null || value.isNaN() || value.isInfinite()) {
            return 0.0;
        }
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public Double calculatePercentage(Long used, Long total) {
        if (total == null || total == 0 || used == null) {
            return 0.0;
        }
        return roundToTwoDecimals(used * 100.0 / total);
    }
}
