package com.timdakwah.letshijrah;

public class UpdateTime {
    public void UpdateTimeClass () {
        Thread t = new Thread() {
            @Override
            public void run () {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e){ }
            }
        };
        t.start();
    }
}
