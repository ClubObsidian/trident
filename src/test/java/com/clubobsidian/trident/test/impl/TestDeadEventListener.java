package com.clubobsidian.trident.test.impl;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.event.DeadEvent;

public class TestDeadEventListener {

    private int timesRan;

    public TestDeadEventListener() {
        this.timesRan = 0;
    }


    @EventHandler
    public void onDeadEvent(DeadEvent event) {
        System.out.println("Dead event: " + event.getDeadEvent().getName());
        this.timesRan += 1;
    }

    public int getTimesRan() {
        return this.timesRan;
    }
}