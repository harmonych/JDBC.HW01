package hw01;

public class PetDeleteMain {

	public static void main(String[] args) {		
		PetDAO dao = new PetDAO();
		int n = dao.delete(4);  //此處輸入要刪除該組資料的id
		if (n==0){
			System.out.println("未有該輸入id組別的資料。");
		}
	}
}
