package Test;

import java.io.File;

public class XulyString {
	public static void main(String[] args) {
		File newDir = new File("C:\\Users\\PhanTheTue\\OneDrive\\Desktop\\Test\\FTP");
		boolean created = newDir.mkdirs();
		if (created) {
			System.out.println("CREATED the directory: ");
		} else {
			System.out.println("COULD NOT create the directory: " );
		}
	}
}
