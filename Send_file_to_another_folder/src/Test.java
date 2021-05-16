import java.io.*; 
import java.nio.file.Files;


import java.nio.file.*; 
  
public class Test 
{ 
    public static void main(String[] args) throws IOException 
    { 
    	String linkto = "C:\\Users\\tuepr\\Desktop\\desk\\";
    	String link = "C:\\Users\\tuepr\\Desktop\\Wireshark_HTTP_Translate.docx";
    	String link2 = "C:\\Users\\tuepr\\Desktop\\Ngày sinh DT2.xlsx";
    	String link3 = "Wireshark_HTTP_Translate.docx";
    	String link4 = "Ngày sinh DT2.xlsx";
		/*
		 * Path temp =
		 * Files.move(Paths.get(link),Paths.get(linkto+link3),StandardCopyOption.
		 * REPLACE_EXISTING); if(temp != null) {
		 * System.out.println("File renamed and moved successfully"); } else {
		 * System.out.println("Failed to move the file"); }
		 */
        Path copy = Files.copy(Paths.get(link2), Paths.get(linkto+link4), StandardCopyOption.REPLACE_EXISTING);
        
        
        if(copy != null) 
        { 
            System.out.println("File renamed and copy successfully"); 
        } 
        else
        { 
            System.out.println("Failed to move the file"); 
        } 
        
    } 
} 