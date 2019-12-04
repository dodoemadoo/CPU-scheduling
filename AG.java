import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

class sortByAG implements Comparator<AGprocess> 
{	
	public int compare(AGprocess p1, AGprocess p2) 
    { 
        if(p1.AG_Factor>p2.AG_Factor)
        	return 1;
        else if(p1.AG_Factor<p2.AG_Factor)
        	return -1;
        return 0;
    }
}

public class AG 
{
	private Scanner scan= new Scanner(System.in);
	private PriorityQueue<AGprocess> queue;
	private int[] npQuantum,pQuantum;
	private int numOfProcess,quantum;
	public AG() 
	{
		System.out.print("Enter the number of processes: ");
	    numOfProcess=scan.nextInt();
		System.out.print("Enter the quantum of processes: ");
	    quantum=scan.nextInt();
	    queue = new PriorityQueue<AGprocess>(numOfProcess);
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
		    queue.add(p);
	    }    
	    npQuantum = new int[numOfProcess];
	    pQuantum = new int[numOfProcess];
	    pQuantum = fillArray(pQuantum, quantum);
	    npQuantum = fill_npQuantum(npQuantum, pQuantum);
	    Iterator<AGprocess> itr = queue.iterator(); 
        while (itr.hasNext()) 
            System.out.println(itr.next().processName);
	}
	
	public static int[] fillArray(int[] arr,int value)
	{
		for (int i = 0; i < arr.length; i++) 
		{
			arr[i] = value;
		}
		return arr;
	}
	
	public static int[] fill_npQuantum(int[] npQuantum, int[] pQuantum) 
	{
		for (int i = 0; i < pQuantum.length; i++) 
		{
			npQuantum[i] = (int) Math.ceil(pQuantum[i]*0.5);
		}
		return npQuantum;
	} 
	
	public static void printArr(int[] arr)
	{
		for (int i = 0; i < arr.length; i++) 
		{
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
}
