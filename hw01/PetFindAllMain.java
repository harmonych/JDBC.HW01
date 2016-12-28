package hw01;

import java.util.List;
import hw01.util.DBUtils;

public class PetFindAllMain {

	public static void main(String[] args) {		
		PetDAO dao = new PetDAO();
		List<PetBean> list = dao.findAll();
		for (PetBean pb: list){
			DBUtils.displayData(pb);
			//預設輸出資料夾為C:\imagesJDBCHW01(圖片)與C:\\txtsJDBCHW01(文字檔)
			//執行後請至該二資料夾確認。
			System.out.println("-------------------------");
		}

	}

}
