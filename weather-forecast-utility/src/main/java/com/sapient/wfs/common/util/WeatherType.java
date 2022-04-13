package com.sapient.wfs.common.util;

public enum WeatherType {
    Rain {
        public String getCustomDescription() {
            return "Carry umbrella!";
        }
    },
    Thunderstorm {
        public String getCustomDescription() {
            return "Thunderstorm";
        }
    },
    Drizzle {
        public String getCustomDescription() {
            return "Carry umbrella!";
        }
    },
    Snow {
        public String getCustomDescription() {
            return "Wear Sweater and Jackets!";
        }
    },
    Atmosphere {
        public String getCustomDescription() {
            return "Enjoy!";
        }
    },
    Clear {
        public String getCustomDescription() {
            return "Clear Sky, Go out and enjoy!";
        }
    },
    Clouds {
        public String getCustomDescription() {
            return "Be prepared";
        }
    };

    public abstract String getCustomDescription();
}
