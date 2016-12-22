package hw01.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import hw01.PetBean;
import hw01.PetDAO;


public class DBUtils {
	public static char[] fileToChars(String filename, String encoding) {
		try (FileInputStream fis = new FileInputStream(filename);
				InputStreamReader in = new InputStreamReader(fis, encoding);
				CharArrayWriter caw = new CharArrayWriter();) {
			int len = 0;
			char[] ca = new char[8192];
			while ((len = in.read(ca)) != -1) {
				caw.write(ca, 0, len);
			}
			return caw.toCharArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] fileToBytes(String filename) {
		File f = new File(filename);
		int len = (int) f.length();
		byte[] b = new byte[len];
		try (FileInputStream fis = new FileInputStream(f);) {
			fis.read(b);
		} catch (Exception ex) {
			ex.getMessage();
		}
		return b;
	}
	public static void saveCharsToFile(char[] comment, File file, String encoding) {
		try (
		   FileOutputStream fos = new FileOutputStream(file);
		   OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
		   PrintWriter pw = new PrintWriter(osw) ;		
		) {
			pw.println(new String(comment));
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
	public static void saveBytesToFile(byte[] picture, File file) {
		try (
		   FileOutputStream fos = new FileOutputStream(file);
		) {
			fos.write(picture);
		} catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	public static void initPlace(String filename, String encoding){
		PetDAO dao = new PetDAO();
		try (
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader in = new InputStreamReader(fis, encoding);
			BufferedReader br = new BufferedReader(in);
		) {
			
			String line = "";
			while ((line = br.readLine()) != null) {
			  String[] pa = line.split(",");
			  int id = Integer.parseInt(pa[0].trim());
			  String petName  = pa[1].trim();
			  String masterName = pa[2].trim();
			  String birthday = pa[3].trim();
//			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			  birthday = sdf.format(date);
			  //Elaborate on Date imports:http://stackoverflow.com/questions/2305973/java-util-date-vs-java-sql-date
			  //TL/DR: DBAs handle date strings which were stored as long type rather well.
			  //       saving as long probably will save me some time. However, this is a practice for DAO project.
			  //       So it would be wise to actually practice instead of work-around.
			  int price = Integer.parseInt(pa[4].trim());
			  double weight = Double.parseDouble(pa[5].trim());
			  String picFileName = pa[6].trim();
			  byte[] picture = DBUtils.fileToBytes("pics\\" + pa[7].trim());
			  char[] comment = DBUtils.fileToChars("txts\\" + pa[8].trim(), encoding);
			  PetBean pb = new PetBean(id, petName, 
				   masterName, birthday, price, weight, 
				   picFileName, picture, comment);
			  dao.insert(pb);

			}
			System.out.println("檔案" + filename + "新增完畢");
		} catch (IOException ex) {
			System.out.println(ex.getMessage() + "==>" + filename);
			ex.printStackTrace();
		}
//	}

//	public static void displayData(PlaceBean pb) {
//		String saveFolderImg = "D:\\images1221";
//		File dirImg = new File(saveFolderImg);
//		if (!dirImg.exists())  dirImg.mkdirs();
//		String filenameImg = pb.getFilename(); 
//		File fileImg = new File(dirImg, filenameImg);
		
		
//		String saveFolderTxt = "D:\\text1221";
//		File dirTxt = new File(saveFolderTxt);
//		if (!dirTxt.exists())  dirTxt.mkdirs();
//		String filenameTxt = "Comment" + pb.getPlaceId() + ".txt"; 
//		File fileTxt = new File(dirTxt, filenameTxt);
//		
//		System.out.println("Place Id :"  + pb.getPlaceId());
//		System.out.println("Type Id  :"  + pb.getTypeId());
//		System.out.println("Name     :"  + pb.getName());
//		System.out.println("Phone    :"  + pb.getPhone());
//		System.out.println("Address  :"  + pb.getAddress());
//		System.out.println("Longitude:"  + pb.getLongitude());
//		System.out.println("Latitude :"  + pb.getLatitude());
//		System.out.println("Link     :"  + pb.getLink());
//		System.out.println("Filename :"  + pb.getFilename());
//		saveBytesToFile(pb.getPicture(), fileImg);
//		saveCharsToFile(pb.getComment(), fileTxt, "BIG5");
	}
}

