package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;

public class hdfsFileWrite {
		
	public static void main(String[] args) throws Exception {
		
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String inputFilePath = "/user/anonymous/1901";
		String outputFilePath = "/user/anonymous/1901-copy";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
	   
	    FSDataInputStream in = null;
	    FSDataOutputStream out = null;
	    
	    try {
	    	
			// Retrieve an instance for the filesystem to use - HDFS
	    	FileSystem fs = FileSystem.get(conf);
	    	// Hadoop DFS Path - Input & Output file
	    	Path inFile = new Path(inputFilePath);
	    	Path outFile = new Path(outputFilePath);
	    	
	    	// Verification
	    	if (!fs.exists(inFile)) {
	    		System.out.println("Input file not found");
	    		throw new IOException("Input file not found");
	    	}
	    	if (fs.exists(outFile)) {
	    		System.out.println("Output file already exists");
	    		throw new IOException("Output file already exists");
	    	}
	    	
	    	try {
	    		// open and read from file
	    		in = fs.open(inFile);
	    		// Create file to write
	    		out = fs.create(outFile);
	    		IOUtils.copyBytes(in, out, 4096, false);
	    		System.out.println("Completing wrting file.");
	        
	    	} finally {
	    		IOUtils.closeStream(in);
	    		IOUtils.closeStream(out);
	    	}      
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    
	  	}	
}
