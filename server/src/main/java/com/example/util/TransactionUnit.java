package com.example.util;

/**
 * @Author dongkw
 * @Date 2021/1/25、4:20 下午
 **/
public abstract class TransactionUnit implements ITransaction {

   protected SagaStatus sagaStatus;

   @Override
   public SagaStatus getStatus() {
      return sagaStatus;
   }
}
