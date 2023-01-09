package net.mikoto.yukino.strategy.impl;

import net.mikoto.yukino.strategy.PrimaryKeyGenerateStrategy;

/**
 * @author mikoto
 * @date 2022/12/25
 * Create for yukino
 */
public class SnowflakePkGenerateStrategy implements PrimaryKeyGenerateStrategy<Long> {
    /**
     * The number of digits the sequence occupies in the id
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final long sequenceBits = 12L;
    /**
     * The start timestamp (2015-01-01)
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final long staterTimestamp = 1420041600000L;
    /**
     * The number of digits occupied by the machine id
     */
    private final long workerIdBits = 5L;

    /**
     * The number of digits occupied by the machine id
     */
    private final long datacenterIdBits = 5L;

    /**
     * The worker ID(0~31)
     */
    private final long workerId;

    /**
     * The datacenter ID(0~31)
     */
    private final long datacenterId;

    /**
     * The sequence in milliseconds (0~4095)
     */
    private long sequence = 0L;

    /**
     * The last timestamp the ID was generated
     */
    private long lastTimestamp = -1L;

    /**
     * Constructor
     *
     * @param workerId     The worker ID (0~31)
     * @param datacenterId The datacenter ID (0~31)
     */
    public SnowflakePkGenerateStrategy(long workerId, long datacenterId) {
        // The maximum supported data identifier id, the result is 31
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        // The maximum supported machine id, the result is 31
        // (this shift algorithm can quickly calculate the maximum decimal number that can be represented
        // by several binary numbers)
        long maxWorkerId = ~(-1L << workerIdBits);

        if (workerId > maxWorkerId || workerId < 0) {
            throw new RuntimeException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new RuntimeException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * Block until the next millisecond until a new timestamp is obtained
     *
     * @param lastTimestamp The last timestamp the ID was generated
     * @return current timestamp
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Returns the current time in milliseconds
     *
     * @return current time (milliseconds)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public Long run(Object... objects) {
        // Generate the mask of the sequence, here is 4095 (0b111111111111=0xfff=4095)
        long sequenceMask = ~(-1L << sequenceBits);
        long timestamp = timeGen();
        // Shift the time truncation to the left by 22 bits(5+5+12)
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        // The data identifier id is shifted to the left by 17 bits(12+5)
        long datacenterIdShift = sequenceBits + workerIdBits;

        // If the current time is less than the timestamp of the last ID generation, it means that the system clock
        // has rolled back by this time and an exception should be thrown
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // Sequence within milliseconds if generated at the same timestamp
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // Sequence overflow in milliseconds
            if (sequence == 0) {
                // Block until the next millisecond, get a new timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // Timestamp changed, sequence reset in milliseconds
        else {
            sequence = 0L;
        }

        // The last timestamp the ID was generated
        lastTimestamp = timestamp;

        // Shift and put together by OR operation to form a 64-bit ID
        return ((timestamp - staterTimestamp) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << sequenceBits)
                | sequence;
    }
}
