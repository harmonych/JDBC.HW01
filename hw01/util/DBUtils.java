package hw01.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import hw01.PetBean;
import hw01.PetDAO;


public class DBUtils {
	public static char[] fileToChars(String filename, String encoding) {
		try (
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader in = new InputStreamReader(fis, encoding);
			CharArrayWriter caw = new CharArrayWriter();
		){
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
//	public static void saveCharsToFile(char[] comment, File file, String encoding) {
//		try (
//		   FileOutputStream fos = new FileOutputStream(file);
//		   OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
//		   PrintWriter pw = new PrintWriter(osw) ;		
//		) {
//			pw.println(new String(comment));
//		} catch(IOException ex){
//			ex.printStackTrace();
//		}
//	}
//	public static void saveBytesToFile(byte[] picture, File file) {
//		try (
//		   FileOutputStream fos = new FileOutputStream(file);
//		) {
//			fos.write(picture);
//		} catch(IOException ex){
//			ex.printStackTrace();
//		}
//		
//	}
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
			  byte[] picture = DBUtils.fileToBytes("pics\\" + pa[6].trim());
			  char[] comment = DBUtils.fileToChars("txts\\" + pa[7].trim(), encoding);
			  PetBean pb = new PetBean(id, petName, masterName, birthday, price,
					  				weight, picFileName, picture, comment);
			  dao.insert(pb);

			}
			System.out.println("檔案" + filename + "新增完畢");
		} catch (IOException ex) {
			System.out.println(ex.getMessage() + "==>" + filename);
			ex.printStackTrace();
		}
	}
	public static void displayData(PetBean pb){
		String saveFolderImg = "C:\\imagesJDBCHW01";
		File dirImg = new File(saveFolderImg);
		if (!dirImg.exists()) dirImg.mkdirs();
		String filenameImg = pb.getFilename();
		File fileImg = new File(dirImg, filenameImg);
		
		String saveFolderTxt = "C:\\txtsJDBCHW01";
		File dirTxt = new File(saveFolderTxt);
		if (!dirTxt.exists()) dirTxt.mkdirs();
		String filenameTxt = "comment" + pb.getId() + ".txt";
		File fileTxt = new File(dirTxt, filenameTxt);

		System.out.println("Id：" + pb.getId());
		System.out.println("寵物名稱：" + pb.getPetName());
		System.out.println("飼主名稱：" + pb.getMasterName());
		System.out.println("生日：" + pb.getBirthday());
		System.out.println("價格：" + pb.getPrice());
		System.out.println("重量：" + pb.getWeight());
		System.out.println("檔案名稱：" + pb.getFilename());
		saveBytesToFile(pb.getPicture(), fileImg);
		saveCharsToFile(pb.getComment(), fileTxt, "UTF8");
	}
	private static void saveBytesToFile(byte[] picture, File fileImg) {
		try(
			FileOutputStream fos = new FileOutputStream(fileImg)
		){
			fos.write(picture);			
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	private static void saveCharsToFile(char[] comment, File fileTxt, String encoding) {
		try(
			FileOutputStream fos = new FileOutputStream(fileTxt);
			OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
			PrintWriter pw = new PrintWriter(osw);
		){
			pw.println(new String(comment));
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}


}

