
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class The_Comparator implements Comparator<Process>
{ 
    public int compare(Process p1, Process p2) 
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
class Process{
String ProcessName;
String ProcessColor;
int ArrivalTime;
int BurstTime;
int waitingTime;
int tournarTime;
}
public class shortestJobFirst 
{
	PriorityQueue<Process> queue;
    shortestJobFirst()
    {
	    Scanner s=new Scanner(System.in); 
	    int numofprocess,At,Bt;  
	    String name,colour;
	    System.out.println("Enter the number of Processes :");
	    numofprocess=s.nextInt();
	    PriorityQueue<Process> queue = new PriorityQueue<Process>(numofprocess,new The_Comparator());
	    for(int i=0;i<numofprocess;i++)
	    {
	    Process p=new Process();
	    name=s.nextLine(); 
	    System.out.println("Enter the name :");
	    name=s.nextLine();  
	    System.out.println("Enter the color :");
	    colour=s.nextLine();
	    System.out.println("Enter the Arrival time :");
	    At=s.nextInt();
	    System.out.println("Enter the Burst Time :");
	    Bt=s.nextInt();
	    p.ArrivalTime=At;
	    p.BurstTime=Bt;
	    p.ProcessName=name;
	    p.ProcessColor=colour;
	    queue.add(p);
	    }
	    makechart(queue);
    }
	public void makechart(PriorityQueue<Process> q)
	{
		 int [][]temp = new int[q.size()][2];
		 PriorityQueue<Process> q2;
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
