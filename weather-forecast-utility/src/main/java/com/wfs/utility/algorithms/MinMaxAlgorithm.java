package com.wfs.utility.algorithms;

import com.wfs.utility.util.CommonUtil;
import com.wfs.utility.vo.Temperature;
import com.wfs.utility.vo.WeatherData;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MinMaxAlgorithm {
    public static Temperature getTemperatureMinMax(List<WeatherData> weatherDataList) {
        Optional<WeatherData> tempMax = weatherDataList.stream().max(Comparator.comparing(i -> i.getMain().getTempMax()));
        Optional<WeatherData> tempMin = weatherDataList.stream().min(Comparator.comparing(i -> i.getMain().getTempMin()));

        Temperature temperature = new Temperature();
        tempMin.ifPresent(weatherData -> temperature.setMinimum(CommonUtil.kelvinToCelsius(weatherData.getMain().getTempMin())));
        tempMax.ifPresent(weatherData -> temperature.setMaximum(CommonUtil.kelvinToCelsius(weatherData.getMain().getTempMax())));

        return temperature;
    }

    public static double getMaxWindSpeed(List<WeatherData> weatherDataList) {
        Optional<WeatherData> maxWindSpeed = weatherDataList.stream().max(Comparator.comparing(i -> i.getWind().getSpeed()));
        return maxWindSpeed.map(weatherData -> CommonUtil.meterPerSecToMPH(weatherData.getWind().getSpeed())).orElse(Double.NEGATIVE_INFINITY);
    }

    private MinMaxAlgorithm() {
    }
}
