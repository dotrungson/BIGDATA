package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;

public class hdfsListFile {
	
	public static void main(String[] args) throws Exception {
		
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String directoryPath = "/user/anonymous/data";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
		
		try {
			// Retrieve an instance for the filesystem to use - HDFS
			FileSystem fs = FileSystem.get(conf);
			// Hadoop DFS Path - Input directory
			Path inputDirectory = new Path(directoryPath);
			  
			// Check if input is valid
			if (!fs.exists(inputDirectory)) {
				System.out.println("Input Directory not found");
				throw new IOException("Input Directory not found");
			}
			
			FileStatus[] status = fs.listStatus(inputDirectory);
			
			Path[] listedPaths = FileUtil.stat2Paths(status);
			
			for (Path p : listedPaths) {
				System.out.println(p);
			}
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
