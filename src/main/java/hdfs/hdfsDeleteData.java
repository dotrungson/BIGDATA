package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;

public class hdfsDeleteData {

public static void main(String[] args) throws Exception {
		
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String inputPath = "/user/anonymous/1901-copy";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
		
		try {
			// Retrieve an instance for the filesystem to use - HDFS
			FileSystem fs = FileSystem.get(conf);
			// Hadoop DFS Path - Input directory
			Path inputFile = new Path(inputPath);
			  
			// Check if input is valid
			if (!fs.exists(inputFile)) {
				System.out.println("Input File not found");
				throw new IOException("Input File not found");
			}
			
			boolean isDeleted = fs.delete(inputFile, false);
			
			if (isDeleted) {
				System.out.println("The file has been deleted.");
			} else {
				System.out.println("The file hasn't been deleted.");
			}
					  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
