package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

public class hdfsFileRead {

	public static void main(String[] args) throws Exception {
		
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String filePath = "/user/anonymous/data/1901.txt";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
		
		try {
			// Retrieve an instance for the filesystem to use - HDFS
			FileSystem fs = FileSystem.get(conf);
			// Hadoop DFS Path - Input file
			Path inFile = new Path(filePath);
			  
			// Check if input is valid
			if (!fs.exists(inFile)) {
				System.out.println("Input file not found");
				throw new IOException("Input file not found");
			}
			
			// open and read from file
			FSDataInputStream in = fs.open(inFile);
			//file content on terminal 
			OutputStream out = System.out;
			
			try {
				
				// system.out as output stream to display
				IOUtils.copyBytes(in, out, 4096, false);
				System.out.println("The offset after reading all lines in file: " + in.getPos());
			} catch (IOException e) {
				System.out.println("Error while copying file");
			} finally {
				   // Closing streams
					IOUtils.closeStream(in);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}		 
	}
}
