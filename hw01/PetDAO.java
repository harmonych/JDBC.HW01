package hw01;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import hw01.util.SystemConstant;

public class PetDAO {
	String dbURL = "jdbc:mysql://" + SystemConstant.HOST + ":3306/" + SystemConstant.DB + "?user=" + SystemConstant.USER +
			"&password=" + SystemConstant.PASSWORD + "&useSSL=true&useUnicode=yes&characterEncoding=UTF-8";

	public PetDAO(String dbURL) {
		super();
		this.dbURL = dbURL;
	}
	
	public PetDAO() {
	}
	
	public void createTables(){
		String createStr = readSQLFile("CreatePet.sql");
		String dropStr = readSQLFile("DropPet.sql");
		
		try (
			Connection con = DriverManager.getConnection(dbURL);
			Statement stmt = con.createStatement();
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
			System.out.println("所提供路徑：" + filename +"\n檔案不存在");
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
			pstmt.setString(3, pb.getBirthday());   
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
			e.printStackTrace();
		}
		return n;
	}
	
	public int update(PetBean pb){
		int n=0;
		String sql = "UPDATE pet SET petName =?, masterName =?, "
				+ " birthday=?, price=?, weight=?, filename=?, "
				+ " picture=?, comment=?"
				+ " WHERE id=?;";
		try(
			Connection con = DriverManager.getConnection(dbURL);
			PreparedStatement pstmt = con.prepareStatement(sql);
		){
			pstmt.setInt(9, pb.getId());
			pstmt.setString(1, pb.getPetName());
			pstmt.setString(2, pb.getMasterName());
			pstmt.setString(3, pb.getBirthday());
			pstmt.setInt(4, pb.getPrice());
			pstmt.setDouble(5, pb.getWeight());
			pstmt.setString(6, pb.getFilename());
			pstmt.setBytes(7, pb.getPicture());
			SerialClob clob = new SerialClob(pb.getComment());
			pstmt.setClob(8, clob);
			n = pstmt.executeUpdate();
			if(n!=0)System.out.println("修改記錄成功, id=" + pb.getId());			
		}catch (SQLException e){
			e.printStackTrace();
		}
		return n;
	}
	
	public int delete(int key){
		int n =0;
		String sql = "DELETE FROM Pet WHERE ID =? ;";
		try(
			Connection con = DriverManager.getConnection(dbURL);
			PreparedStatement pstmt = con.prepareStatement(sql);
		){
			pstmt.setInt(1, key);
			n = pstmt.executeUpdate();
			if(n!= 0)System.out.println("刪除記錄成功。id為"+ key + "的該組資料已被刪除。");
			//當有改變資料內容時才回報刪除成功。
		}catch (SQLException e){
			e.printStackTrace();
		}
		return n;
	}
	
	public PetBean findByPrimaryKey(int key){
		String sql = "SELECT * FROM pet"
				+ " WHERE id =?;";
		PetBean pb = null;
		try(
			Connection con = DriverManager.getConnection(dbURL);
			PreparedStatement pstmt = con.prepareStatement(sql);
		){
			pstmt.setInt(1, key);
			try(
				ResultSet rs = pstmt.executeQuery();
			){
				if (rs.next()){
					pb = new PetBean();
					pb.setId(rs.getInt(1));
					pb.setPetName(rs.getString(2));
					pb.setMasterName(rs.getString(3));
					pb.setBirthday(rs.getString(4));
					pb.setPrice(rs.getInt(5));
					pb.setWeight(rs.getDouble(6));
					pb.setFilename(rs.getString(7));
					pb.setPicture(rs.getBytes(8));
					pb.setComment(clobToCharArray(rs.getClob(9)));	
				}
			}
			System.out.println("查詢紀錄成功, id =" + pb.getId());
		}catch (SQLException e){
			e.printStackTrace();
		}
		return pb;
	}
	
	private char[] clobToCharArray(Clob clob) {
		try(
			Reader r = clob.getCharacterStream();
			CharArrayWriter caw = new CharArrayWriter();	
		){
			char[] ca = new char[8192];
			int len = 0;
			while ((len=r.read(ca))!=-1){
				caw.write(ca,0,len);
			}
			char[] op = caw.toCharArray();
			return op;
		}catch (SQLException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	public List<PetBean> findAll(){
		List<PetBean> list = new ArrayList<>();
		PetBean pb = null;
		String sql = "SELECT * FROM Pet;";
		try(
			Connection con = DriverManager.getConnection(dbURL);
			PreparedStatement pstmt = con.prepareStatement(sql);
		){
			try(
				ResultSet rs = pstmt.executeQuery(sql);
			){
				while(rs.next()){
					pb = new PetBean();
					pb.setId(rs.getInt(1));
					pb.setPetName(rs.getString(2));
					pb.setMasterName(rs.getString(3));
					pb.setBirthday(rs.getString(4));
					pb.setPrice(rs.getInt(5));
					pb.setWeight(rs.getDouble(6));
					pb.setFilename(rs.getString(7));
					pb.setPicture(rs.getBytes(8));
					pb.setComment(clobToCharArray(rs.getClob(9)));
					list.add(pb);
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return list;
	}

}
