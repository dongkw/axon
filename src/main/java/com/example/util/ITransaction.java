package com.example.util;

import java.util.List;

/**
 * @Author dongkw
 * @Date 2021/1/25、4:11 下午
 **/
public interface ITransaction {

    SagaStatus getStatus();

    List<Class> getEventRegList();

    void start();

    void rollback();

    void eventHandler(Object event);

    Object fill(Object cmd);
}
