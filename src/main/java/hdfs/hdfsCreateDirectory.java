package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;

public class hdfsCreateDirectory {
	
	public static void main(String[] args) throws Exception {
	
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String directoryPath = "/user/anonymous/Test";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
		
		try {
			// Retrieve an instance for the filesystem to use - HDFS
			FileSystem fs = FileSystem.get(conf);
			// Hadoop DFS Path - directory
			Path directory = new Path(directoryPath);
			  
			// Check if input is valid
			if (fs.exists(directory)) {
				System.out.println("The directory exists");
				throw new IOException("The directory exists");
			}
			
			boolean isCreated = fs.mkdirs(directory);

			if (isCreated) {
				System.out.println("Directory created");
			} else {
				System.out.println("Directory creation failed");
			}
			  
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
