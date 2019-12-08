import java.util.ArrayList;
import java.util.Scanner;

class runningProcess
{
	int startTime,endTime;
	String name;
}

public class AG 
{
	private Scanner scan= new Scanner(System.in);
	private ArrayList<AGprocess> readyprocessQueue = new ArrayList<AGprocess>();
	private ArrayList<runningProcess>runningprocessQueue = new ArrayList<runningProcess>();
	private AGprocess[] processQueue;
	private int[] npQuantum,pQuantum;
	private AGprocess currProcess = null;
	private runningProcess rp = null;
	private int numOfProcess,quantum,processFT,time=0;
	public AG() 
	{
		System.out.print("Enter the number of processes: ");
	    numOfProcess=scan.nextInt();
		System.out.print("Enter the quantum of processes: ");
	    quantum=scan.nextInt();
	    processQueue = new AGprocess[numOfProcess];
	    for(int i=0;i<numOfProcess;i++)
	    {
		    scan = new Scanner(System.in);
		    processQueue[i] = new AGprocess();
		    System.out.print("Enter the name: ");
		    processQueue[i].processName = scan.nextLine();  
		    System.out.print("Enter the color: ");
		    processQueue[i].processColor = scan.nextLine();
		    System.out.print("Enter the arrival time: ");
		    processQueue[i].arrivalTime = scan.nextInt();
		    System.out.print("Enter the burst Time: ");
		    processQueue[i].burstTime = scan.nextInt();
		    System.out.print("Enter the priority: ");
		    processQueue[i].priority = scan.nextInt();
		    processQueue[i].AG_Factor = processQueue[i].priority + processQueue[i].arrivalTime + processQueue[i].burstTime;
	    }  
	    npQuantum = new int[numOfProcess];
	    pQuantum = new int[numOfProcess];
	    pQuantum = fill_pQuantum(pQuantum, quantum);
	    npQuantum = fill_npQuantum(npQuantum, pQuantum);
	    while (numOfProcess!=0) 
	    {
		    fillRQ(readyprocessQueue, processQueue, time);
			if (readyprocessQueue.size()!=0 || currProcess!=null) 
			{
				if (currProcess==null)
				{
					currProcess = readyprocessQueue.remove(0);
					rp = new runningProcess();
					rp.startTime = time;
					rp.name = currProcess.processName;
					int index = getProcessIndex(currProcess.processName, processQueue);
					if ( npQuantum[index] < currProcess.burstTime) 
					{
						/*if(pQuantum[index] <= currProcess.burstTime)
							
						else*/
							
					}
					else 
					{
						time += currProcess.burstTime;
						currProcess.burstTime = 0;
						rp.endTime = time;
						runningprocessQueue.add(rp);
						currProcess = null;
					}
				}
				else
				{
					
				}
			}
			else
				time++;
			
		}
	}
	
	public static int[] fill_pQuantum(int[] arr,int value)
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
	
	public static AGprocess getMin_AT_AG(ArrayList<AGprocess> rq)
	{
		AGprocess minProcess = rq.get(0);
		for (int i = 1; i < rq.size(); i++) 
		{
			if (minProcess.arrivalTime < rq.get(i).arrivalTime) 
			{
				minProcess = rq.get(i);
			}
			else if (minProcess.arrivalTime == rq.get(i).arrivalTime) 
			{
				if (minProcess.AG_Factor > rq.get(i).AG_Factor)
				{
					minProcess = rq.get(i);
				}
			}
		}
		return minProcess;
	}
	
	public static AGprocess getMinAG(ArrayList<AGprocess> rq)
	{
		AGprocess minProcess = rq.get(0);
		for (int i = 1; i < rq.size(); i++)
		{
			if(minProcess.AG_Factor > rq.get(i).AG_Factor)
				minProcess = rq.get(i);
		}
		return minProcess;
	}
	public static Boolean fillRQ(ArrayList<AGprocess> rq, AGprocess[] q,int t) 
	{
		Boolean check = false;
		for (int i = 0; i < q.length; i++)
		{
			if (q[i].arrivalTime <= t && !rq.contains(q[i]))
			{
				check = true;
				rq.add(q[i]);
			}
		}
		return check;
	}
	
	public static int getProcessIndex(String key , AGprocess[] pq) 
	{
		for (int i = 0; i < pq.length; i++) 
		{
			if(pq[i].processName.equals(key))
				return i;
		}
		return -1;
	}
}
