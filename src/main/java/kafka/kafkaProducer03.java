package kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;
import java.io.*;
import java.util.*;

public class kafkaProducer03 {

	public static void main(String[] args) {
		String filePath = "data/1902";
		String broker = "172.19.0.2:6667";
		String topic = "topic02";
		List<String> allData = readAllData(filePath);
		//Create a kafka producer
		KafkaProducer<String, String> producer = getKafkaProducer(broker);
		//Sending messages to Kafka
		for(String data : allData) {
			//Creating producer record
			ProducerRecord<String, String> record = new ProducerRecord<>(topic, data);
			//Sending a message asynchronously
			producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.out.println("Exception occured = " + exception);
                }
                if(metadata != null) {
                	System.out.print("Message produced, topic: " + metadata.topic() + " ");
    				System.out.print("partition : " + metadata.partition()+ " ");
    				System.out.println("offset: " + metadata.offset());
                }
            });
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
			
	private static List<String> readAllData(String path) { //path is the Absolute path of the file	
		List<String> allData = new ArrayList<>();
        String line;  	
        try {
			Scanner input = new Scanner(new File(path)); 
			while (input.hasNext()) {
				line = input.nextLine();
				allData.add(line);
			}
			input.close();
		}
		catch (Exception ex) {
			System.out.println("The file doesn't exist.");
		}	      
        System.out.println("Total line count including header = " + allData.size());
        return allData;
    }		
}

class DemoProducerCallback implements Callback {
	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception e) {
		if(e != null) {
            e.printStackTrace();
         } 
	}
}

class ProducerCallback implements Callback {
	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception e) {
		if (e != null) {
            System.out.println("Exception occured = " + e);
        }
        if(recordMetadata != null) {
            System.out.println("producing data to topic: " + recordMetadata.topic() + " at offset " + recordMetadata.offset());
        }
	}
}