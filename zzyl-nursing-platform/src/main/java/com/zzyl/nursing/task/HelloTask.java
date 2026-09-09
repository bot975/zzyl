package com.zzyl.nursing.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloTask {
    public void myTask(){
        System.out.println("HelloTask");
    }
}
