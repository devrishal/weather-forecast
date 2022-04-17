package com.sapient.wfs.common.algorithms;

import com.sapient.wfs.common.util.CommonUtil;
import com.sapient.wfs.common.vo.Temperature;
import com.sapient.wfs.common.vo.WeatherData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MinMaxAlgorithm {
    public static Temperature getMinMax(List<WeatherData> weatherDataList) {
        Double temp_max = Double.MIN_VALUE;
        Double temp_min = Double.MAX_VALUE;
        log.info("Inside Maximum and minimum Temperature for the current day");
        Temperature temperature = new Temperature();
        for (int i = 0; i < weatherDataList.size(); i++) {
            temp_max = Math.max(temp_max, CommonUtil.kelvinToCelsius(weatherDataList.get(i).getMain().getTemp_max()));
            temp_min = Math.min(temp_min, CommonUtil.kelvinToCelsius(weatherDataList.get(i).getMain().getTemp_min()));
        }
        temperature.setMinimum(temp_min);
        temperature.setMaximum(temp_max);
        return temperature;
    }
}
