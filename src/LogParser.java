import beans.Endpoint;
import beans.LogRow;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LogParser {

    private static Map<Endpoint, LogRow> parsedData;
    private static List<LogRow> sortedData;

    private static void printTopFive(){

        int count = 0;
        sortedData = new ArrayList<>(parsedData.values());
        Collections.sort(sortedData);
        System.out.println("Method    " + " URL           " + "Frequency   ");
        for(LogRow row : sortedData){
            if(++count > 5){
                break;
            }
            Endpoint endpoint = row.getEndpoint();
            System.out.println(endpoint.getMethod() + "       " + endpoint.getUrl() + "     " + row.getCount());
        }
    }

    private static void printAllData(){

        System.out.println("Method" + "        URL" + "                     Min Time" + "                  Max Time" + "                        Avg Time");
        for(LogRow row : sortedData){
            Endpoint endpoint = row.getEndpoint();
            System.out.println(endpoint.getMethod() + "         " + endpoint.getUrl() + "                " + row.getMinTime() + "                 " + row.getMaxTime()  + "                        " + row.getAvgTime()/row.getCount()) ;
        }
    }

    private static void updateLogData(LogRow newRow){

        if(newRow == null){
            return;
        }

        LogRow row;
        if(parsedData.containsKey(newRow.getEndpoint())){
            row = parsedData.get(newRow.getEndpoint());
            row.setCount(row.getCount()+1);
            int newResponseTime = newRow.getResponseTime();
            if(row.getMinTime() > newResponseTime){
                row.setMinTime(newResponseTime);
            }
            if(row.getMaxTime() < newResponseTime){
                row.setMaxTime(newResponseTime);
            }
            row.setAvgTime(newResponseTime + row.getAvgTime());
        }else{

            row = newRow;
        }

        parsedData.put(row.getEndpoint(), row);
    }

    private static LogRow getLogRowObjectByLine(String line){
        line = line.replaceAll("/[0-9]+", "/{id}");
        String[] tokenizedRow = line.split(",");
        if(tokenizedRow[0].equalsIgnoreCase("timestamp"))
            return null;
        String url = tokenizedRow[1];
        String method = tokenizedRow[2];
        int responseTime = Integer.parseInt(tokenizedRow[3]);

        LogRow row = new LogRow.LogRowBuilder(url, method).setCount(1)
                .setResponseTime(responseTime)
                .setMaxTime(responseTime)
                .setAvgTime(responseTime)
                .setMinTime(responseTime).build();
        return row;
    }

    private static void pushLogDataToStore(String filePath){
        FileInputStream inputStream = null;
        BufferedReader in;
        try{
            inputStream = new FileInputStream(filePath);

            in = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = in.readLine()) != null) {
                LogRow row = getLogRowObjectByLine(line);
                if(row != null){
                    updateLogData(row);
                }else
                    continue;;
            }

        }catch (Exception e){
            System.out.println("exception " + e);
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final String LogFilePath = "/Users/ashish/Documents/NetBeansProjects/LogParser2/src/SampleInputLogParser.csv";
        parsedData = new HashMap<>();
        pushLogDataToStore(LogFilePath);


        printTopFive();
        System.out.println("\nall data \n");
        printAllData();
    }
}
