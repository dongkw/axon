package com.example.aggregate.bean.status;

import com.example.aggregate.bean.command.*;

/**
 * @Author dongkw
 * @Date 2021/1/25、2:41 下午
 **/
public abstract class IstrStage {
    public void create(CreateCmd cmd) {

    }

    public void update(UpdateCmd cmd) {

    }

    public void cancel(CancelCmd cmd) {

    }

    public void distribute(DistributeCmd cmd) {

    }

    public void aprvPass(AprvPassCmd cmd) {

    }


}
