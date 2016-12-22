package hw01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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
		){
			
		}catch (FileNotFoundException e){
			System.out.println("所提供路徑：" + filename +"\n沒有檔案");
		}
		return null;
	}

	public int insert(PetBean pb){
		int n =0;
		return n;
	}
	
	public int update(PetBean pb){
		int n=0;
		return n;
	}
	
	public int delete(int key){
		
	}
	
	public PetBean findByPrimaryKey(int key){
		
	}
	
	public List<PetBean> findAll(){
		return null;
	}

}
