package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;

public class hdfsFileStatus {
	
	public static void main(String[] args) throws Exception {
		
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String filePath = "/user/anonymous/1901";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
		
		try {
			// Retrieve an instance for the filesystem to use - HDFS
			FileSystem fs = FileSystem.get(conf);
			// Hadoop DFS Path - Input file
			Path inputFile = new Path(filePath);
			  
			// Check if input is valid
			if (!fs.exists(inputFile)) {
				System.out.println("Input file not found");
				throw new IOException("Input file not found");
			}
			
			FileStatus status = fs.getFileStatus(inputFile) ;
			
			System.out.println("path :" + status.getPath().toString());
			System.out.println("isDir :" + status.isDirectory());
			System.out.println("Length :" + status.getLen());		
			System.out.println("Modification time :" + status.getModificationTime());		
			System.out.println("Replication :" + status.getReplication());
			System.out.println("Block Size :" + status.getBlockSize());
			System.out.println("owner :" + status.getOwner());
			System.out.println("group :" + status.getGroup());
			System.out.println("permission :" + status.getPermission().toString());
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
