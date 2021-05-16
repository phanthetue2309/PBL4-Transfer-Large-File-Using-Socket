import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUpload_File_Directory  {
	 public static void recursiveDelete(File file) {
	        //to end the recursive loop
	        if (!file.exists())
	            return;
	        
	        //if directory, go inside and call recursively
	        if (file.isDirectory()) {
	            for (File f : file.listFiles()) {
	                //call recursively
	                recursiveDelete(f);
	            }
	        }
	        //call delete to delete files and empty directory
	        file.delete();
	        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
	    }
	public static void main(String[] args) throws Exception {
		String server = "localhost";
	//	int port = 21;
		String user = "tuepro123@gmail.com";
		String pass = "Dolynhan0105";
		FTPClient ftpClient = null;

		try {
			ftpClient = new FTPClient();
		//	ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
			ftpClient.connect(server); // connect and login to the server	
			
			  int reply;
			  reply = ftpClient.getReplyCode(); 
			  if(!FTPReply.isPositiveCompletion(reply)) { ftpClient.disconnect(); 
			  throw new	Exception("Exception in connecting to FTP Server"); }
			 
			ftpClient.login(user, pass);	
			ftpClient.enterLocalPassiveMode(); // use local passive mode to pass firewall
			
			String remoteDirPath = "/"; // location cho thu muc 
		//	String remoteDirPath = "/"; // location cho file 
			String localDirPath = "C:\\Users\\tuepr\\Desktop\\ahihi";
			
		//	FTPUtil.uploadDirectory(ftpClient, remoteDirPath, localDirPath, "");
		//	FTPUtil.uploadSingleFile(ftpClient, localDirPath, remoteDirPath);
			// directory on the server to be downloaded

			 
			// directory where the files will be saved
			String saveDirPath = "C:\\Users\\tuepr\\Desktop\\desk";
			 
			// call the utility method
		//	FTPUtil.downloadDirectory(ftpClient, remoteDirPath, "", saveDirPath);
			
//			String currentDir = "/Test/League of Legends/Replays";
//			String saveDir = "C:/Users/PhanTheTue/OneDrive/Desktop";
//			
//			
//				File newDir = new File(saveDir + "/" + currentDir);
//				boolean created = newDir.mkdirs();
//				if (created) {
//					System.out.println("CREATED the directory: " + saveDir);
//				} else {
//					System.out.println("COULD NOT create the directory: " + saveDir);
//				}
//				
//			FTPUtil.downloadDirectory(ftpClient, "", currentDir, saveDir);
//			
//			File from = new File(saveDir +"/"+ currentDir);
//			File to = new File(saveDir + "/" + "Replays");
//
//			boolean success = from.renameTo(to);
//			if (success) {
//				System.out.println("Directory Moved Successfully");
//			} else {
//				System.out.println("Directory Moved Failed");
//			}
//			
//			File last = new File(saveDir + "/" + "Test" );
//			recursiveDelete(last);
//			last.delete();

			FTPUtil.Sync(ftpClient, "");
			
			
			ftpClient.logout(); // log out and disconnect from the server
			ftpClient.disconnect();	
			System.out.println("Disconnected");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}