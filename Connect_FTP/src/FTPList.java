import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
 
/**
 * An example program that demonstrates how to list files and directories
 * on a FTP server using Apache Commons Net API.
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
    private static void printFileDetails(FTPClient ftpclient, FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName(); // get ten file 
            if (file.isDirectory()) {
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
 
            System.out.println(details);
        }
    }
 

 
    private static void showServerReply(FTPClient ftpclient,FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}