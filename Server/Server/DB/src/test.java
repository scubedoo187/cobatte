
public class test 
{
	public static void main(String[] args) 
	{
		LinkDatabase db = new LinkDatabase();
		String a;
		a = null;
		//db.signup("임해성", "222");
		//db.createRoom("test", "ddd", "sss", 12, 30);
		//db.enterARoom("ong21", "leesin");
		//a = db.enterARoom("ong21", "taskkiller");
		a = db.enterARoom("ong21", "bright");
		if(a == null)
		System.out.println("입력");
		else
			System.out.println(a);

	}
}
