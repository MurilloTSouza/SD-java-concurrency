package br.edu.ifpb.producer;

import br.edu.ifpb.buffer.BufferStrategy;

import java.util.Random;

public class Producer implements Runnable{

    private static Random random = new Random();
    private BufferStrategy sharedBuffer;

    public Producer(BufferStrategy sharedBuffer){
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        for(int value = 1; value <=10; value ++){
            try{
                Thread.sleep(random.nextInt(3000));
                sharedBuffer.set(value);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("@Producer: Job Finished!");
    }
}
