
package com.litethinking.app.service.impl;

import com.litethinking.app.service.FxService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class FxServiceImpl implements FxService {

    @Value("${app.fx.usd:0.00025}")
    private BigDecimal usdRate; // COP -> USD

    @Value("${app.fx.eur:0.000232}")
    private BigDecimal eurRate; // COP -> EUR

    private static final int SCALE = 2;

    @Override
    public BigDecimal toUsd(BigDecimal cop) {
        if (cop == null) return null;
        return cop.multiply(usdRate).setScale(SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal toEur(BigDecimal cop) {
        if (cop == null) return null;
        return cop.multiply(eurRate).setScale(SCALE, RoundingMode.HALF_UP);
    }
}
