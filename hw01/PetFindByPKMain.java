package hw01;

import hw01.util.DBUtils;

public class PetFindByPKMain {

	public static void main(String[] args) {
		
		PetDAO dao = new PetDAO();
		dao.createTables();
		DBUtils.initPlace("Pet.txt", "UTF8");
	}

}
