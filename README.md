# SD-java-concurrency
Simple demonstration of multithread concurrency using **Lock** and **ArrayBlockingQueue**.

## How to run
Run `sh ./run.sh` in the root folder

You can also run the Java .class directly from the classes/ folder

## How it works
- One thread called **Producer** will set values from 1 to 10 to a shared **Buffer** object
- That **Buffer** object can store a maximun of 3 values

- A second thread called **Consumer** will retrieve values from the **Buffer**
, following the FIFO order (First In First Out).

- If **Producer** tryies to set a value and the **Buffer** is full, the **Prodcuer** thread
will be locked until a buffer is available.

- In the end, the **Consumer** will display a sum of all values retrieved from the **Buffer**,
in that case: 55.
