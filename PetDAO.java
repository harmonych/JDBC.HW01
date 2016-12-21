package hw01;

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
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void createTables(){
		
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
	
	public PlaceBean findByPrimaryKey(int key){
		
	}
	
	public List<PlaceBean> findAll(){
		return null;
	}


}
