package br.edu.ifpb;

import br.edu.ifpb.buffer.BufferBlocking;
import br.edu.ifpb.buffer.BufferStrategy;
import br.edu.ifpb.buffer.CircularBuffer;
import br.edu.ifpb.consumer.Consumer;
import br.edu.ifpb.producer.Producer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(2);

        BufferStrategy sharedBuffer;
        sharedBuffer = new BufferBlocking(3);
        // sharedBuffer = new CircularBuffer(3);

        try{
            pool.execute(new Producer(sharedBuffer));
            pool.execute(new Consumer(sharedBuffer));

        } catch (Exception e){
            e.printStackTrace();
        }

        pool.shutdown();

    }
}
