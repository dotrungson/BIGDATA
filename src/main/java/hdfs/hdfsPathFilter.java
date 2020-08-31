package hdfs;

import java.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;

public class hdfsPathFilter {
	
	public static void main(String[] args) throws Exception {
		
		String hdfsUrl = "hdfs://172.19.0.2:8020";
		String directoryPath = "/user/anonymous/*";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", hdfsUrl);
		
		try {
			// Retrieve an instance for the filesystem to use - HDFS
			FileSystem fs = FileSystem.get(conf);
			// Hadoop DFS Path - Input directory
			Path inputDirectory = new Path(directoryPath);
			  	
			FileStatus[] status = fs.globStatus(inputDirectory, new RegexExcludePathFilter("hdfs://172.19.0.2:8020/user/anonymous/1901"));
			
			Path[] listedPaths = FileUtil.stat2Paths(status);
			
			for (Path p : listedPaths) {
				System.out.println(p);
			}
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

class RegexExcludePathFilter implements PathFilter {
	private final String regex;
	
	public RegexExcludePathFilter(String regex) {
		this.regex = regex;
	} 
	
	public boolean accept(Path path) {
		return !path.toString().matches(regex);
	}
}
