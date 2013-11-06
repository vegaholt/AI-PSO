package com.ui;

import task1.Swarm;

public class SwarmThread implements Runnable {

    private final Swarm swarm;
    public boolean run;
    private boolean terminate;
    private int simcount;

    public SwarmThread(Swarm swarm) {
        this.swarm = swarm;
    }

    @Override
    public void run() {
        while (!terminate) {
            if (run) {
                for (int i = 0; i < simcount; i++) {
                    swarm.initSwarm();
                    swarm.run();
                }
                run = false;
            }
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startSimulation(int n) {
        simcount = n;
        run = true;
    }
    private void terminate(){
        this.terminate = true;
    }
}
