
public class test 
{
	public static void main(String[] args) 
	{
		UserInfo_DB db = new UserInfo_DB();
		String a;
		
		db.single_hard("������", 351245);
		db.single_hard("������", 332856);
		db.single_hard("���ο�", 284416);
		db.single_hard("���ؼ�", 274422);
		db.single_hard("������", 313306);
		
		a = db.select_hard_rank();
		
		System.out.println(a);
	}
}
