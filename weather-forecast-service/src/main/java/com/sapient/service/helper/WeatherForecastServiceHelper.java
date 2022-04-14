package com.sapient.service.helper;

import com.sapient.wfs.common.vo.WeatherData;
import com.sapient.wfs.common.constants.ApplicationConstants;
import com.sapient.wfs.common.constants.MessageConstants;
import com.sapient.wfs.common.util.CommonUtil;
import com.sapient.wfs.common.util.WeatherConditionCodeLoader;

public enum WeatherForecastServiceHelper {
    TEMPERATURE {
        @Override
        public String getMessage(WeatherData weatherInformation) {
            return (CommonUtil.kelvinToCelsius(weatherInformation.getMain().getTemp_max())) > 40 ? MessageConstants.HIGH_TEMPERATURE_MSG : "";
        }
    },
    WEATHER_TYPE {
        @Override
        public String getMessage(WeatherData weatherInformation) {
            /**
             * API DOCUMENTATION
             * NOTE: It is possible to meet more than one weather condition for a requested location. The first weather condition in API respond is primary.
             */
            if (weatherInformation.getWeather() != null && weatherInformation.getWeather().get(0).getId() >= 500 && weatherInformation.getWeather().get(0).getId() <= 531) {
                String weatherConditionID = String.valueOf(weatherInformation.getWeather().get(0).getId());
                return WeatherConditionCodeLoader.weatherConditionCode.get(weatherConditionID).getMessage();
            }
            return "";
        }
    },
    WIND {
        @Override
        public String getMessage(WeatherData weatherInformation) {
            return CommonUtil.meterPerSecToMPH(weatherInformation.getWind().getSpeed()) > 10 ? MessageConstants.WINDY_MESSAGE : "";
        }
    },
    COMMON_CHECK {
        @Override
        public String getMessage(WeatherData weatherInformation) {
            String message = TEMPERATURE.getMessage(weatherInformation);
            if (TEMPERATURE.getMessage(weatherInformation).equals(ApplicationConstants.BLANK)
                    && WEATHER_TYPE.getMessage(weatherInformation).equals(ApplicationConstants.BLANK)
                    && WIND.getMessage(weatherInformation).equals(ApplicationConstants.BLANK)) {
                return MessageConstants.COMMON_MESSAGE;
            }
            return message;

        }
    };

    public abstract String getMessage(WeatherData weatherInformation);

}
