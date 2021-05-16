// upload va download file

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPUtil {
	public static void recursiveDelete(File file) {
		// to end the recursive loop
		if (!file.exists())
			return;

		// if directory, go inside and call recursively
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				// call recursively
				recursiveDelete(f);
			}
		}
		// call delete to delete files and empty directory
		file.delete();
		System.out.println("Deleted file/folder: " + file.getAbsolutePath());
	}

	public static void uploadDirectory(FTPClient ftpClient, String remoteDirPath, String localParentDir,
			String remoteParentDir) throws IOException {

		if (checkDirectoryExists(ftpClient, remoteDirPath) == false) {
			boolean success = ftpClient.makeDirectory(remoteDirPath);
			if (success) {
				System.out.println("Successfully created directory: " + remoteDirPath);
			} else {
				System.out.println("Failed to create directory. See server's reply.");
			}
		} else {
			System.out.println("The folder has already exits.");
		}
		System.out.println("LISTING directory: " + localParentDir);
		File localDir = new File(localParentDir);
		File[] subFiles = localDir.listFiles();
		if (subFiles != null && subFiles.length > 0) {
			for (File item : subFiles) {
				String remoteFilePath = remoteDirPath + "/" + remoteParentDir + "/" + item.getName();
				if (remoteParentDir.equals("")) {
					remoteFilePath = remoteDirPath + "/" + item.getName();
				}

				if (item.isFile()) {
					// upload the file
					String localFilePath = item.getAbsolutePath();
					System.out.println("Upload the file: " + localFilePath);

					boolean uploaded = uploadSingleFile(ftpClient, localFilePath, remoteFilePath);
					if (uploaded) {
						System.out.println("UPLOADED SUCCESS a file to: " + remoteFilePath);
					} else {
						System.out.println("COULD NOT upload the file: " + localFilePath);
					}
				} else {
					// create directory on the server
					boolean created = ftpClient.makeDirectory(remoteFilePath);
					if (created) {
						System.out.println("CREATED the directory: " + remoteFilePath);
					} else {
						System.out.println("COULD NOT create the directory: " + remoteFilePath);
					}

					// upload the sub directory
					String parent = remoteParentDir + "/" + item.getName();
					if (remoteParentDir.equals("")) {
						parent = item.getName();
					}

					localParentDir = item.getAbsolutePath();
					uploadDirectory(ftpClient, remoteDirPath, localParentDir, parent);
				}
			}
		}
	}

	public static boolean checkDirectoryExists(FTPClient ftpClient, String dirPath) throws IOException {
		int returnCode = ftpClient.getReplyCode();
		ftpClient.changeWorkingDirectory(dirPath);
		returnCode = ftpClient.getReplyCode();
		if (returnCode == 550) {
			return false;
		}
		return true;
	}

	public static boolean uploadSingleFile(FTPClient ftpClient, String localFilePath, String remoteFilePath)
			throws IOException {
		InputStream inputStream = new FileInputStream(new File(localFilePath));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.storeFile(remoteFilePath, inputStream);
		} finally {
			inputStream.close();
		}
	}

	public static void downloadDirectory(FTPClient ftpClient, String parentDir, String currentDir, String saveDir)
			throws IOException {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		if (subFiles != null && subFiles.length > 0) {
			for (FTPFile aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and the directory itself
					continue;
				}
				String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				String newDirPath = saveDir + parentDir + File.separator + currentDir + File.separator
						+ currentFileName;
				if (currentDir.equals("")) {
					newDirPath = saveDir + parentDir + File.separator + currentFileName;
				}

				if (aFile.isDirectory()) {
					// create the directory in saveDir
					File newDir = new File(newDirPath);
					boolean created = newDir.mkdirs();
					if (created) {
						System.out.println("CREATED the directory: " + newDirPath);
					} else {
						System.out.println("COULD NOT create the directory: " + newDirPath);
					}

					// download the sub directory
					downloadDirectory(ftpClient, dirToList, currentFileName, saveDir);
				} else {
					// download the file
					boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);
					if (success) {
						System.out.println("DOWNLOADED the file: " + filePath);
					} else {
						System.out.println("COULD NOT download the file: " + filePath);
					}
				}
			}
		}
	}

	public static boolean downloadSingleFile(FTPClient ftpClient, String remoteFilePath, String savePath)
			throws IOException {
		File downloadFile = new File(savePath);

		File parentDir = downloadFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static void Sync(FTPClient ftpClient, String link) {
		String saveDir = System.getProperty("user.home") + "\\OneDrive\\Desktop\\FTP-access2";

		try {

			FTPFile[] subFiles = ftpClient.listFiles(link);
			if (subFiles != null && subFiles.length > 0) {
				for (FTPFile aFile : subFiles) { // vòng for kiểm tra trong thư mục

					File tmpDir = new File(saveDir + "\\" + link + "\\" + aFile.getName());
					boolean exists = tmpDir.exists();
					if (aFile.isDirectory()) {
						if (exists) {
							Sync(ftpClient, link + "/" + aFile.getName());
						} else {
							String currentDir = link + "/" + aFile.getName();
							String[] solve = currentDir.split("/");
							if (solve.length > 2) {
								File newDir = new File(saveDir + "/" + currentDir);

								boolean created = newDir.mkdirs();
								if (created) {
									System.out.println("CREATED the directory: " + saveDir + currentDir);
								} else {
									System.out.println("COULD NOT create the directory: " + saveDir + currentDir);
								}

								FTPUtil.downloadDirectory(ftpClient, "", currentDir, saveDir);
								JOptionPane.showMessageDialog(null, "DOWNLOAD SUCCESS");
							} else {
								FTPUtil.downloadDirectory(ftpClient, "", currentDir, saveDir);
								JOptionPane.showMessageDialog(null, "DOWNLOAD SUCCESS");
								System.out.println(
										saveDir + link + "/" + aFile.getName() + " is directory and not exists");
								FTPUtil.downloadDirectory(ftpClient, "", link + "/" + aFile.getName(),
										saveDir + link + "/" + aFile.getName());
							}
						}

					} else {
						if (exists)
							continue;
						else {
							System.out.println(saveDir + "\\" + aFile.getName() + " is file and not exists");
							FTPUtil.downloadSingleFile(ftpClient, link + "/" + aFile.getName(), saveDir + "/" +  link + "/" + aFile.getName());
						}
					}
				}
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}