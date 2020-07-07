package beans;

import java.util.Objects;

public class LogRow implements Comparable<LogRow> {

    private final Endpoint endpoint;
    private int responseTime;
    private int responseCode;
    private int maxTime;
    private int minTime;
    private double avgTime;
    private int count;
    private long timestamp;

    private LogRow(LogRowBuilder builder){
        this.endpoint = new Endpoint(builder.url, builder.method);
        this.responseCode = builder.responseCode;
        this.responseTime = builder.responseTime;
        this.maxTime = builder.maxTime == 0 ? builder.responseTime : builder.maxTime;
        this.minTime = builder.minTime == 0 ? builder.responseTime : builder.minTime;
        this.avgTime = builder.avgTime == 0 ? builder.responseTime : builder.avgTime;
        this.count = builder.count;
        this.timestamp = builder.timestamp;
    }

    public int getMinTime() {
        return minTime;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public int getCount() {
        return count;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public static class LogRowBuilder{
        private final String url;
        private final String method;
        private int responseTime;
        private int responseCode;
        private int maxTime;
        private int minTime;
        private double avgTime;
        private int count;
        private long timestamp;

        public LogRowBuilder(String url, String method){
            this.url = url;
            this.method = method;
        }

        public LogRowBuilder setResponseTime(int responseTime) {
            this.responseTime = responseTime;
            return this;
        }

        public LogRowBuilder setResponseCode(int responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public LogRowBuilder setMaxTime(int maxTime) {
            this.maxTime = maxTime;
            return this;
        }

        public LogRowBuilder setMinTime(int minTime) {
            this.minTime = minTime;
            return this;
        }

        public LogRowBuilder setAvgTime(double avgTime) {
            this.avgTime = avgTime;
            return this;
        }

        public LogRowBuilder setCount(int count) {
            this.count = count;
            return this;
        }

        public LogRowBuilder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public LogRow build(){
            return new LogRow(this);
        }


    }

    @Override
    public int compareTo(LogRow o) {
        return o.getCount() - count;
    }
}
