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
		    scan = new Scanner(System.in);	
		    AGprocess p = new AGprocess();
		    System.out.print("Enter the name: ");
		    p.processName = scan.nextLine();  
		    System.out.print("Enter the color: ");
		    p.processColor = scan.nextLine();
		    System.out.print("Enter the arrival time: ");
		    p.arrivalTime = scan.nextInt();
		    System.out.print("Enter the burst Time: ");
		    p.burstTime = scan.nextInt();
		    System.out.print("Enter the priority: ");
		    p.priority = scan.nextInt();
		    p.AG_Factor = p.priority + p.arrivalTime + p.burstTime;
	    }      
	}
}
