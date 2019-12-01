import java.util.Scanner;

public class Main 
{
	public static Scanner scan = new Scanner(System.in);
	public static String choice;
	private static shortestJobFirst sjf;
	private static shortestRemainingTimeFirst srtf;
	private static AG ag;
	private static priorityScheduling pScheduling;
	public static void main(String[] args) 
	{
		System.out.println("Enter (1) to make shortest job first scheduling.");
		System.out.println("Enter (2) to make shortest remaining time first scheduling.");
		System.out.println("Enter (2) to make priority scheduling.");
		System.out.println("Enter (4) to make AG scheduling.");
		System.out.print("Enter your choice: ");
		choice = scan.nextLine();
		switch (choice) {
		case "1":
			sjf = new shortestJobFirst();
			break;
		case "2":
			srtf = new shortestRemainingTimeFirst();
			break;
		case "3":
			pScheduling = new priorityScheduling();
			break;
		case "4":
			ag = new AG();
			break;
		default:
			System.out.println("Invalid input..!");
			break;
		}
	}

}
