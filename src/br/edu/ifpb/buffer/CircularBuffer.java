package br.edu.ifpb.buffer;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularBuffer implements BufferStrategy {

    private Lock lock = new ReentrantLock();
    private Condition canSet = lock.newCondition();
    private Condition canRead = lock.newCondition();

    private int[] buffers;
    private int emptyBuffers;
    private int setIndex = 0;
    private int getIndex = 0;

    public CircularBuffer(int capacity){
        buffers = new int[capacity];
        Arrays.fill(buffers, -1); // instantiate buffers with -1 as default value
        emptyBuffers = buffers.length; // all buffers are empty
    }

    @Override
    public void set(int value) {
        lock.lock();

        try {
            // if all buffers are occupied
            // wait for signal when CONSUMER get a value
            while(emptyBuffers == 0) {
                System.out.printf(
                        "@Buffer: PRODUCER trying to set with no buffer available.\n" +
                            "\t| PRODUCER waiting for signal....\n");
                canSet.await();
            }

            // if has empty buffer or canSet is unlocked, set value to buffer...
            buffers[setIndex] = value;
            System.out.printf(
                    "@Buffer: PRODUCER set value:\n" +
                        "\t| setting %2d to buffers[%2d]\n",
                    value, setIndex);

            // ...reduce number of available buffers...
            emptyBuffers--;
            // ...and increase setIndex, and return to 0 if bigger the array length
            setIndex = (setIndex +1) % buffers.length;

            // then send signal so CONSUMER can get
            canRead.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public int get() {
        lock.lock();
        int currentValue = 0;

        try {
            // if all buffers are empty
            // wait for signal when PRODUCER set a value
            while(emptyBuffers == buffers.length){
                System.out.printf(
                        "@Buffer: CONSUMER trying to get with all buffers empty.\n" +
                                "\t| CONSUMER waiting for signal...\n");
                canRead.await();
            }

            // if buffer has value or canRead is unlocked, retrieve value from buffer...
            currentValue = buffers[getIndex];
            System.out.printf(
                    "@Buffer: CONSUMER read value:\n" +
                        "\t| getting %2d from buffer[%2d]\n", currentValue, getIndex);

            // ...increase number of available buffers...
            emptyBuffers++;
            // ...and increase getIndex, and return to 0 if bigger the array length
            getIndex = (getIndex +1) % buffers.length;

            // then send signal so PRODUCER can set
            canSet.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return currentValue;
    }
}
