// download and upload file success

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


public class FTPUploader {

	FTPClient ftp = null;
	static String host = "192.168.3.13";
	static String user = "FTP-user";
	static String pwd = "230920";

	public FTPUploader(String host, String user, String pwd) throws Exception {
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;
		ftp.connect(host);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		ftp.login(user, pwd);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
	}

	public void uploadFile(String localFileFullName, String fileName, String hostDir) throws Exception {
		try (InputStream input = new FileInputStream(new File(localFileFullName))) {
			this.ftp.storeFile(hostDir + fileName, input);
		}
	}

	public void downloadFile(String localfile) {
		FileOutputStream fos = null;
		try {
			ftp.connect(host); 	//	ftp.login("OS", "Nhanlydo");
			ftp.login(user, pwd);
			ftp.setFileType(FTP.BINARY_FILE_TYPE); // download file convert ve binary 
		//	ftp.enterLocalPassiveMode();
			// Create an OutputStream for the file
			String filename = localfile;
			fos = new FileOutputStream(filename);
			// Fetch file from server
			ftp.retrieveFile("/" + filename, fos);		
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (fos != null)
				System.out.print("Download Success \n");
		}
	}

	public void disconnect() {
		if (this.ftp.isConnected()) {
			try {
				this.ftp.logout();
				this.ftp.disconnect();
			} catch (IOException f) {
				// do nothing as file is already saved to server
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Start");
		FTPUploader ftpUploader = new FTPUploader(host, user, pwd);
		ftpUploader.uploadFile("C:\\Users\\tuepr\\Desktop\\Weekly Schedule .xlsx", "WeeklySchedule.xlsx", "/Code_Python");
		// ftpUploader.uploadFile("C:\\Users\\tuepr\\Desktop\\py3.png", "python.txt",
		// "");
		ftpUploader.downloadFile("py3.png");
		ftpUploader.disconnect();
	}

}