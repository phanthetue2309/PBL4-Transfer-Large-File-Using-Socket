import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPCreateDir {

	private static void showServerReply(FTPClient ftpClient) {
		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String aReply : replies) {
				System.out.println("SERVER: " + aReply);
			}
		}
	}

	public static void main(String[] args) {
		String server = "192.168.3.13";
		//	int port = 21;
		String user = "FTP-user";
		String pass = "230920";

		FTPClient ftpClient = new FTPClient();

		try {

			ftpClient.connect(server);
			showServerReply(ftpClient);

			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("Operation failed. Server reply code: " + replyCode);
				return;
			}

			boolean success = ftpClient.login(user, pass);
			showServerReply(ftpClient);

			if (!success) {
				System.out.println("Could not login to the server");
				return;
			}

			// Creates a directory
			String dirToCreate = "/Code_Python/MitCutenhat";
			success = ftpClient.makeDirectory(dirToCreate);
			showServerReply(ftpClient);

			if (success) {
				System.out.println("Successfully created directory: " + dirToCreate);
			} else {
				System.out.println("Failed to create directory. See server's reply.");
			}

			// logs out
			ftpClient.logout();
			ftpClient.disconnect();

		} catch (IOException ex) {
			System.out.println("Oops! Something wrong happened");
			ex.printStackTrace();
		}
	}

}
