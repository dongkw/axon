package com.example.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author dongkw
 * @Date 2021/1/26、5:49 下午
 **/
@Slf4j
public class SyncController {

    private final Map<String, Object> responseMap;

    private final Map<String, CyclicBarrier> lockMap;

    public SyncController() {
        responseMap = new ConcurrentHashMap<>();
        lockMap = new ConcurrentHashMap<>();
    }

    public Object waitResponse(String requestId) {
        log.info("wait response:{}", requestId);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        lockMap.put(requestId, cyclicBarrier);
        try {
            cyclicBarrier.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            e.printStackTrace();
        }
        Object obj = responseMap.get(requestId);
        responseMap.remove(requestId);
        return obj;
    }

    public void syncResponse(String requestId, Object response) {
        responseMap.put(requestId, response);
        try {
            lockMap.get(requestId).await();
            lockMap.remove(requestId);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        log.info("sync response:{},{}", requestId, response);
    }
}
