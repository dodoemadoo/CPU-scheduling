
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class The_Comparator implements Comparator<scheduling>
{ 
    public int compare(scheduling p1, scheduling p2) 
    { 
        if(p1.ArrivalTime>p2.ArrivalTime)
        {
        return 1;
        }
        else if(p1.ArrivalTime<p2.ArrivalTime){
        return -1;
        }
        return 0;
    } 
}

public class shortestJobFirst 
{
	PriorityQueue<scheduling> queue;
	private Scanner scan = new Scanner(System.in);
	int numOfProcesses;
    shortestJobFirst()
    {
	    System.out.print("Enter the number of processes: ");
	    numOfProcesses=scan.nextInt();
	    queue = new PriorityQueue<scheduling>(numOfProcesses,new The_Comparator());
	    for(int i=0;i<numOfProcesses;i++)
	    {
		    scheduling p=new scheduling();
		    System.out.print("Enter the name :");
		    p.ProcessName=scan.nextLine();  
		    System.out.print("Enter the color :");
		    p.ProcessColor=scan.nextLine();
		    System.out.print("Enter the Arrival time :");
		    p.ArrivalTime=scan.nextInt();
		    System.out.print("Enter the Burst Time :");
		    p.BurstTime=scan.nextInt();
		    queue.add(p);
	    }
	    makechart(queue);
    }
	public void makechart(PriorityQueue<scheduling> q)
	{
		 int [][]temp = new int[q.size()][2];
		 PriorityQueue<scheduling> q2;
		 q2=q;
		 for(int i=0;i<q.size();i++)
		 {
		       for(int j=0;j<2;j++)
		       {
		           if(j==0)
		           temp[i][j]=q2.peek().ArrivalTime;
		           else
		             temp[i][j]=q2.poll().BurstTime;
		       }
		 }
		 for(int i=0;i<q.size();i++)
		 {
		      for(int j=0;j<2;j++)
		      {
		           System.out.println(temp[i][j]);	      
		      }
		 }
	}
}
