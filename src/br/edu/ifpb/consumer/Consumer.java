package br.edu.ifpb.consumer;

import br.edu.ifpb.buffer.BufferStrategy;

import java.util.Random;

public class Consumer implements Runnable{

    private static Random random = new Random();
    private BufferStrategy sharedBuffer;

    public Consumer(BufferStrategy sharedBuffer){
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        int sum = 0;

        for(int count=1; count<=10; count++){
            try {
                Thread.sleep(random.nextInt(3000));
                sum += sharedBuffer.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf(
                "@Consumer: Job Finished!\n" +
                    "\t| Final sum: %2d\n", sum);
    }
}
