package hw01;

import hw01.util.DBUtils;

public class PetUpdateMain {

	public static void main(String[] args) {
		byte[] picture = DBUtils.fileToBytes("pics/Kitty.jpg");
		char[] comment = DBUtils.fileToChars("txts/Kitty1.txt", "UTF8");
		PetDAO dao = new PetDAO();
		PetBean pb = new PetBean(3, "孫凱蒂", "孫慧霞", "2015-04-30" , 750,
									 3.85, "Kitty.jpg", picture, comment);
		int n = dao.update(pb);
        if (n >=1 ){
        	System.out.println("修改紀錄成功。");
        }else{
        	System.out.println("未修改任何紀錄。");
        }
	}
}
