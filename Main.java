import java.util.Scanner;

public class Main 
{
	public static Scanner scan = new Scanner(System.in);
	public static String choice;
	private static shortestJobFirst sjf;
	private static shortestRemainingTimeFirst srtf;
	private static AG agScheduling;
	private static priorityScheduling pScheduling;
	public static void main(String[] args) 
	{
		System.out.println("Enter (1) to make shortest job first scheduling.");
		System.out.println("Enter (2) to make shortest remaining time first scheduling.");
		System.out.println("Enter (2) to make priority scheduling.");
		System.out.println("Enter (4) to make ag scheduling.");
		System.out.print("Enter your choice: ");
		choice = scan.nextLine();
		switch (choice) {
		case "1":
			setSjf(new shortestJobFirst());
			break;
		case "2":
			setSrtf(new shortestRemainingTimeFirst());
			break;
		case "3":
			setpScheduling(new priorityScheduling());
			break;
		case "4":
			setAgScheduling(new AG());
			break;
		default:
			System.out.println("Invalid input..!");
			break;
		}
	}
	public static shortestJobFirst getSjf() {
		return sjf;
	}
	public static void setSjf(shortestJobFirst sjf) {
		Main.sjf = sjf;
	}
	public static shortestRemainingTimeFirst getSrtf() {
		return srtf;
	}
	public static void setSrtf(shortestRemainingTimeFirst srtf) {
		Main.srtf = srtf;
	}
	public static AG getAgScheduling() {
		return agScheduling;
	}
	public static void setAgScheduling(AG agScheduling) {
		Main.agScheduling = agScheduling;
	}
	public static priorityScheduling getpScheduling() {
		return pScheduling;
	}
	public static void setpScheduling(priorityScheduling pScheduling) {
		Main.pScheduling = pScheduling;
	}

}
