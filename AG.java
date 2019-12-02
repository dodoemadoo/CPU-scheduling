import java.util.Comparator;
import java.util.Scanner;


public class AG 
{
	private Scanner scan= new Scanner(System.in);
	private int numOfProcess;
	public AG() 
	{
		System.out.print("Enter the number of processes: ");
	    numOfProcess=scan.nextInt();
	    for(int i=0;i<numOfProcess;i++)
	    {
		    process p=new AGprocess();
		    System.out.print("Enter the name: ");
		    p.processName=scan.nextLine();  
		    System.out.print("Enter the color: ");
		    p.processColor=scan.nextLine();
		    System.out.print("Enter the Arrival time: ");
		    p.arrivalTime=scan.nextInt();
		    System.out.print("Enter the Burst Time: ");
		    p.burstTime=scan.nextInt();
	    }      
	}
}
