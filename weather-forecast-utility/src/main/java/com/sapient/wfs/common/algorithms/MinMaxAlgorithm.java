package com.sapient.wfs.common.algorithms;

import com.sapient.wfs.common.vo.WeatherData;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class MinMaxAlgorithm {
    private Double temp_min;
    private Double temp_max;

    public void getMinMax(List<WeatherData> weatherDataList) {
        this.temp_max = Double.MIN_VALUE;
        this.temp_min = Double.MAX_VALUE;
        for (int i = 0; i < weatherDataList.size(); i++) {
            temp_max = Math.max(temp_max, weatherDataList.get(i).getMain().getTemp_max());
            temp_min = Math.min(temp_min, weatherDataList.get(i).getMain().getTemp_min());
        }
    }
}
