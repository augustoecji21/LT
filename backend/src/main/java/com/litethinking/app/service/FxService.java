
package com.litethinking.app.service;

import java.math.BigDecimal;

public interface FxService {
    BigDecimal toUsd(BigDecimal cop);
    BigDecimal toEur(BigDecimal cop);
}
