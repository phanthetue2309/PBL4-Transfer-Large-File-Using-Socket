package Test;

import java.io.File;

public class JavaFileDirectoryExistsExample
{
  public static void main(String[] args)
  {
    // test "/var/tmp" directory
	String filecheck = System.getProperty("user.home") + "\\OneDrive\\Desktop\\FTP-access2";
	System.out.println(filecheck);
    File tmpDir = new File(filecheck);
   
    boolean exists = tmpDir.exists();
    if (tmpDir.isDirectory()) 
    {
    	if (exists)
    		System.out.println(filecheck + " is directory and exists");
    }
    else 
    {
    	if (exists)
    		System.out.println(filecheck + " is file and exists");
    }

    // test to see if a file exists
    File file = new File("/Users/al/.bash_history");
    exists = file.exists();
    if (file.exists() && file.isFile())
    {
      System.out.println("file exists, and it is a file");
    }
  }
}