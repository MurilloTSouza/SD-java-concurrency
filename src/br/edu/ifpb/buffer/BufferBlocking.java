package br.edu.ifpb.buffer;

import java.util.concurrent.ArrayBlockingQueue;

public class BufferBlocking implements BufferStrategy {

    private final Integer CAPACITY;
    private ArrayBlockingQueue<Integer> buffer;

    public BufferBlocking(Integer capacity){
        CAPACITY = capacity;
        buffer = new ArrayBlockingQueue<>(CAPACITY);
    }

    @Override
    public void set(int value) {
        try {
            buffer.put(value);
            printSetting(value);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int get() {
        int readValue = 0;

        try{
            readValue = buffer.take();
            printTaking(readValue);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return readValue;
    }

    private void printTaking(int value){
        System.out.printf(
                "@Buffer: CONSUMER taking value: %2d\n" +
                    "\t| %2d of %2d buffers occupied.\n",
                value, buffer.size(), CAPACITY);
    }

    private void printSetting(int value){
        System.out.printf(
                "@Buffer: PRODUCER setting value: %2d\n" +
                    "\t| %2d of %2d buffers occupied.\n",
                value, buffer.size(), CAPACITY);
    }

}
