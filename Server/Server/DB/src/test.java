
public class test 
{
	public static void main(String[] args) 
	{
		UserInfo_DB db = new UserInfo_DB();
		String a;
		
		db.single_hard("¾ûµ¢ÀÌ", 351245);
		db.single_hard("¾ûµ¢ÀÌ", 332856);
		db.single_hard("Á¤¹Î¿ì", 284416);
		db.single_hard("ÀÓÇØ¼º", 274422);
		db.single_hard("Á¤¿µ±Õ", 313306);
		
		a = db.select_hard_rank();
		
		System.out.println(a);
	}
}
