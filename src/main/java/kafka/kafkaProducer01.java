package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class kafkaProducer01 {

	public static void main(String[] args) throws Exception{
		/*
        if(args.length != 3) {
            System.out.println("Please provide <broker> <topic> <csvfilepath>");
            System.exit(0);
        }
        */
		String broker = "172.19.0.2:6667";
		String topic = "topic01";
		String filePath = "data/1901";
		
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MINUTE, 10);
        KafkaProducer<String, String> producer = getKafkaProducer(broker); //Create a kafka producer
        List<String> allData = readAllData(filePath); //Read all lines of the file into a List
        
        for(String data : allData) { //Convert each element of the List into message, then insert to kafka broker
           
        	//Sending a message to kafka broker
        	producer.send(new ProducerRecord<>(topic, data), (metadata, exception) -> {
                if (exception != null) {
                    System.out.println("Exception occured = "+exception);
                }
                if(metadata != null) {
                    System.out.println("producing data to topic  = "+metadata.topic());
                }
            });
        	
            try {
                Thread.sleep(5000);
            }catch(Exception exception) {
                System.out.println("Exception occured = "+exception);
            }
            
        }
    }

    private static KafkaProducer<String, String> getKafkaProducer(String bootStrapServer) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        addAdditionalProps(props);
        return new KafkaProducer<String, String>(props, new StringSerializer(), new StringSerializer());      
    }

    private static void addAdditionalProps(Properties props) {
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("max.request.size", 52000000);
        props.put("max.block.ms",10000);
        props.put("request.timeout.ms",5000);
    }

    private static List<String> readAllData(String path) throws Exception { //path is the Absolute path of the file	
    	List<String> allData = new ArrayList<>();
        String line;  	
    	try (BufferedReader br = new BufferedReader(new FileReader(path))) {      
            while ((line = br.readLine()) != null) { //loop until the end of the file
                allData.add(line); //add line to the list
            }
    	}  	      
        System.out.println("Total line count including header = "+allData.size());
        return allData;
    }
}