package hw01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import hw01.util.SystemConstant;

public class PetDAO {
	String dbURL = SystemConstant.URL + "?user=" + SystemConstant.USER +
			"&password=" + SystemConstant.PASSWORD + "&useSSL=true&useUnicode=yes&characterEncoding=UTF-8";

	public PetDAO(String dbURL) {
		super();
		this.dbURL = dbURL;
	}
	
	public PetDAO() {
	}
	
	public void createTables(){
		String createStr = readSQLFile("CreatePlace.sql");
		String dropStr = readSQLFile("DropPlace.sql");
		
		try (
			Connection con = DriverManager.getConnection(dbURL);
			Statement stmt = con.createStatement(); //為何不用prepared?
		){
			stmt.executeUpdate(dropStr);
			stmt.executeUpdate(createStr);
			System.out.println("表格重置成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String readSQLFile(String filename) {
		try(
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis,"BIG5");
			BufferedReader br = new BufferedReader(isr);
				
		){
			String line = "";
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine())!=null){
				sb.append(line);
			}
			return sb.toString();
		}catch (FileNotFoundException e){
			System.out.println("所提供路徑：" + filename +"\n沒有檔案");
		}catch (IOException e ){
			e.printStackTrace();
		}
		return null;
	}

	public int insert(PetBean pb){
		int n =0;
		String sql = "INSERT INTO Pet "
				+ " VALUES(null, ?, ?, ? ,?, ?, ?, ? ,?)"; 
		try(
			Connection con = DriverManager.getConnection(dbURL);
			PreparedStatement pstmt	= con.prepareStatement(sql);				
		){
			pstmt.setString(1, pb.getPetName());
			pstmt.setString(2, pb.getMasterName());
			pstmt.setDate(3, pb.getBirthday());   //should I use TimeStamp here?
			pstmt.setInt(4, pb.getPrice());
			pstmt.setDouble(5, pb.getWeight());
			pstmt.setString(6, pb.getFilename());
			pstmt.setBytes(7, pb.getPicture());
			SerialClob clob = new SerialClob(pb.getComment());
			pstmt.setClob(8, clob);
			n = pstmt.executeUpdate();
			System.out.println("表格更新成功，id = " + pb.getId());			
		}catch (SQLException e){
			System.out.println("SQL資料錯誤，id = " + pb.getId());
			e.printStackTrace();}
		return n;
	}
	
	public int update(PetBean pb){
		int n=0;
		return n;
	}
	
	public int delete(int key){
		int n =0;
		return n;
	}
	
	public PetBean findByPrimaryKey(int key){
		return null;
	}
	
	public List<PetBean> findAll(){
		return null;
	}

}
