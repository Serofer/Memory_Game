import java.util.Scanner;
public class bin�rsystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welche Zahl vom dekadischen System m�chten Sie �bersetzen?");
		int M=scanner.nextInt();
		System.out.println("In welches System m�chten Sie es �bersetzen? (Hinweis: 16 f�r hexa und 2 f�r bin�r eingeben.");
		int n=scanner.nextInt();
		
		String number = "";
		if (n==2) {
			if (M==0);
			System.out.println(M);
			
			while (M!= 0) {
				
				int Rest = M%n;
				M= M/n;
				number += Rest;
				
			}
			
			System.out.println("Your binary code is "+new StringBuilder(number).reverse().toString());
		
		//System.out.println(M/16+ "    "+M %16);
	}

}
}
