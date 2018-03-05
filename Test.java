public class Test{
	public static void main(String [] args){
		System.out.println(SocialNetwork.editDistance("LISTY", "FISTS"));
		System.out.println(SocialNetwork.editDistance("HI", "HI"));
		System.out.println(SocialNetwork.editDistance("HI", "HE"));
		System.out.println(SocialNetwork.editDistance("HI", "SHE"));
	}
}