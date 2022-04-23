package com.wfs.utility.util;

import com.wfs.utility.algorithms.LRUCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LRUCacheBuilder {
    private static LRUCache lruCache;
    private static final int CACHE_CAPACITY = 100;

    public static LRUCache getLruCache() {
        return lruCache;
    }

    private static void setLruCache(LRUCache lruCache) {
        LRUCacheBuilder.lruCache = lruCache;
    }

    @Bean
    public void build() {
        setLruCache(new LRUCache(CACHE_CAPACITY));
    }

}
