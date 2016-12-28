package hw01;

import hw01.util.DBUtils;

public class PetInsertMain {

	public static void main(String[] args) {
		byte[] picture = DBUtils.fileToBytes("pics/Kitty.jpg");  //自行輸入要放入檔案的檔案路徑
		char[] comment = DBUtils.fileToChars("txts/Kitty1.txt", "UTF8");  //自行輸入要放入檔案的檔案路徑
		PetDAO dao = new PetDAO();
		PetBean pb = new PetBean(3, "孫凱蒂", "孫慧霞", "2015-04-30" , 750,
				 3.85, "Kitty.jpg", picture, comment);
		//PetBean pb = new PetBean(4, "史酷比", "薛奇", "1969-09-13" , 755,
		//							 4.85, "Scoobie.jpg", picture, comment);  //可自行測試其他額外檔案。
		int n = dao.insert(pb);
        if (n ==1 ){
        	System.out.println("新增一筆" + pb.getPetName() + "紀錄成功。");
        }else{
        	System.out.println("未修改任何紀錄。");
        }
	}
}
