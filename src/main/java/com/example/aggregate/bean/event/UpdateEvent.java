package com.example.aggregate.bean.event;

import lombok.Data;

/**
 * @Author dongkw
 * @Date 2021/1/25、1:28 下午
 **/
@Data
public class UpdateEvent {
    private String id;
    private String data;
}
