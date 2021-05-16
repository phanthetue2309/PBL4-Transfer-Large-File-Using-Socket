package Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * An example program that demonstrates how to list files and directories on a
 * FTP server using Apache Commons Net API.
 * 
 * @author www.codejava.net
 */
public class FTPList {

//    public static void main(String[] args) {
//        String server = "192.168.43.113";
//      //  int port = 21;
//        String user = "FTP-user";
//        String pass = "230920";
// 
//        FTPClient ftpClient = new FTPClient();
// 
//        try {
// 
//            ftpClient.connect(server);
//            showServerReply(ftpClient);
// 
//            int replyCode = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(replyCode)) {
//                System.out.println("Connect failed");
//                return;
//            }
// 
//            boolean success = ftpClient.login(user, pass);
//           
//            showServerReply(ftpClient);
// 
//            if (!success) {
//                System.out.println("Could not login to the server");
//                return;
//            }
// 
//            // Lists files and directories
//            FTPFile[] files1 = ftpClient.listFiles("/"); // thu muc goc mac dinh
//            
//            printFileDetails(files1);
//            // uses simpler methods
//            String[] files2 = ftpClient.listNames(); // lay list cua name 
//            printNames(files2);
// 
// 
//        } catch (IOException ex) {
//            System.out.println("Oops! Something wrong happened");
//            ex.printStackTrace();
//        } finally {
//            // logs out and disconnects from server
//            try {
//                if (ftpClient.isConnected()) {
//                    ftpClient.logout();
//                    ftpClient.disconnect();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
	public static void printFileDetails(FTPClient ftpclient, FTPFile[] files, JTable table, JTable tableFile) {
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		DefaultTableModel modelFile = (DefaultTableModel) tableFile.getModel();
		
	
		
		for (FTPFile file : files) {
			String details = file.getName(); // get ten file
			if (file.isDirectory()) {
				details = "[" + details + "]";

				Vector<String> row = new Vector<String>();
				row.add(file.getName());
				row.add("FTP-user");
				row.add(dateFormater.format(file.getTimestamp().getTime()));
				
				model.addRow(row);
//            	details += "\t\t" + file.getSize();
//            	details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
			} else {
				
				Vector<String> row = new Vector<String>();
				row.add(file.getName());
				row.add("FTP-user");
				row.add(dateFormater.format(file.getTimestamp().getTime()));
				
				modelFile.addRow(row);

			}
		}
	}

	public static void printNames(String files[]) { // ham chi co muc dich la in ra ten file
		if (files != null && files.length > 0) {
			for (String aFile : files) {
				System.out.println(aFile);
			}
		}
	}

}