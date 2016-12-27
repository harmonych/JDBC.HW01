package hw01;

import hw01.util.DBUtils;

public class PetDeleteMain {

	public static void main(String[] args) {		
		PetDAO dao = new PetDAO();
		dao.delete(4);  //此處輸入要刪除該組資料的id
	}

}
