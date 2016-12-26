package hw01;

import hw01.util.DBUtils;

public class PetFindAllMain {

	public static void main(String[] args) {		
		PetDAO dao = new PetDAO();
		PetBean pb = dao.findByPrimaryKey(3);
		if (pb!= null){
			DBUtils.displayData(pb);
		}else{
			System.out.println("查無此項主鍵之資料");
		}
	}

}
