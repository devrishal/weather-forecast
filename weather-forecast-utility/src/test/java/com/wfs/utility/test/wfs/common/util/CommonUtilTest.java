package com.wfs.utility.test.wfs.common.util;

import com.wfs.utility.util.CommonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonUtilTest {
    @Test
    void testKelvinToCelcius() {
        Double actual = CommonUtil.kelvinToCelsius(293.58);
        Assertions.assertEquals(20.43D, actual);
    }

    @Test
    void testMeterPerSecToMPH() {
        Double actualMph = CommonUtil.meterPerSecToMPH(10.92);
        Assertions.assertEquals(24.43, actualMph);
    }

    @Test
    void isMessageBlank() {
        Assertions.assertTrue(CommonUtil.isMessageBlank(null));
        Assertions.assertTrue(CommonUtil.isMessageBlank(""));
        Assertions.assertFalse(CommonUtil.isMessageBlank("NotBlank"));
    }

}
