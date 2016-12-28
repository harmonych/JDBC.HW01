package hw01;

import hw01.util.DBUtils;

public class PetFindByPKMain {

	public static void main(String[] args) {		
		PetDAO dao = new PetDAO();
		PetBean pb;
		try {
			pb = dao.findByPrimaryKey(3);
			if (pb!= null){
				DBUtils.displayData(pb);
				//預設輸出資料夾為C:\imagesJDBCHW01(圖片)與C:\\txtsJDBCHW01(文字檔)
				//執行後請至該二資料夾確認。
			}else{
				System.out.println("查無此項主鍵之資料");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("未有該項主鍵資料。");
		}
		
	}

}
