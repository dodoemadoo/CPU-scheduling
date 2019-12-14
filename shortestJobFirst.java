import java.util.ArrayList;
import java.util.Scanner;

public class shortestJobFirst 
{
	private Scanner scan= new Scanner(System.in);
	private ArrayList<SJFprocess> readyprocessQueue = new ArrayList<SJFprocess>();
	private ArrayList<runningProcess>runningprocessQueue = new ArrayList<runningProcess>();
	private SJFprocess[] processQueue;
	private int[] TAT,WT;
	private SJFprocess currProcess = null;
	private runningProcess rp = null;
	private int numOfProcess,time=0;
	double AWT,ATAT;
    shortestJobFirst()
    {
    	System.out.print("Enter the number of processes: ");
	    numOfProcess=scan.nextInt();
	    processQueue = new SJFprocess[numOfProcess];
	    for(int i=0;i<numOfProcess;i++)
	    {
		    scan = new Scanner(System.in);
		    processQueue[i] = new SJFprocess();
		    System.out.print("Enter the name: ");
		    processQueue[i].processName = scan.nextLine();  
		    System.out.print("Enter the arrival time: ");
		    processQueue[i].arrivalTime = scan.nextInt();
		    System.out.print("Enter the burst Time: ");
		    processQueue[i].burstTime = scan.nextInt();
		    processQueue[i].cpBrustTime = processQueue[i].burstTime;
	    }  
	    TAT = new int[numOfProcess];
	    WT = new int[numOfProcess];
	    while (numOfProcess!=0) 
	    {
	    	readyprocessQueue = fillRQ(readyprocessQueue,runningprocessQueue ,processQueue, time);
			if (readyprocessQueue.size()!=0) 
			{
				rp = new runningProcess();
				currProcess = getMinBrust(readyprocessQueue);
				readyprocessQueue.remove(currProcess);
				rp.name = currProcess.processName;
				rp.startTime = time;
				time += currProcess.burstTime;
				rp.endTime = time;
				runningprocessQueue.add(rp);
				processQueue[getProcessIndex(currProcess.processName, processQueue)].burstTime = 0;
				numOfProcess--;
			}
			else 
				time++;
	    }
	    for (int i = 0; i < runningprocessQueue.size(); i++) 
	    {
			System.out.println(runningprocessQueue.get(i).name+" "+runningprocessQueue.get(i).startTime+" "+runningprocessQueue.get(i).endTime);
		}
	    
	    TAT = calTAT(TAT, runningprocessQueue, processQueue);
	    WT = calWT(WT, processQueue, TAT);
	    ATAT = calATAT(TAT);
	    AWT = calAWT(WT);
	    printArr(TAT);
	    System.out.println("--------------------------------------------------------------");
	    printArr(WT);
	    System.out.println("--------------------------------------------------------------");
	    System.out.println(ATAT+" "+AWT);
    }
    
    public static void printArr(int[] arr)
	{
		for (int i = 0; i < arr.length; i++) 
		{
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	
    public static ArrayList<SJFprocess> fillRQ(ArrayList<SJFprocess> rq,ArrayList<runningProcess>rp, SJFprocess[] q,int t) 
	{
		for (int i = 0; i < q.length; i++)
		{
			if (q[i].arrivalTime <= t && !rq.contains(q[i]) && q[i].burstTime!=0 )
			{
				rq.add(q[i]);
			}
		}
		return rq;
	}
    
    public static int getProcessIndex(String key , SJFprocess[] pq) 
	{
		for (int i = 0; i < pq.length; i++) 
		{
			if(pq[i].processName.equals(key))
				return i;
		}
		return -1;
	}
    
	public static SJFprocess getMinBrust(ArrayList<SJFprocess> rq)
	{
		boolean check = false;
		SJFprocess minProcess = new SJFprocess();
		minProcess.burstTime = 1000000000;
		for (int i = 0; i < rq.size(); i++)
		{
			if(minProcess.burstTime > rq.get(i).burstTime)
			{
				check = true;
				minProcess = rq.get(i);
			}
		}
		if(check)
			return minProcess;
		return minProcess;
	}
    
	public static runningProcess getIndex(String key ,ArrayList<runningProcess>RP) 
	{
		for (int i = 0; i < RP.size(); i++)
		{
			if (key.equals(RP.get(i).name))
				return RP.get(i);
		}
		return null;
	}
	
    public int[] calTAT(int[] arr,ArrayList<runningProcess>RP, SJFprocess[] QP) 
	{
		for (int i = 0; i < arr.length; i++)
		{
			runningProcess rp = getIndex(QP[i].processName, RP);
			int index = RP.indexOf(rp);
			arr[i] = RP.get(index).endTime - QP[i].arrivalTime;
		}
		return arr;
	}
	
	public int[] calWT(int[] arr, SJFprocess[] QP,int[] TAT) 
	{
		for (int i = 0; i < arr.length; i++) 
		{
			arr[i] = TAT[i] - QP[i].cpBrustTime;
		}
		return arr;
	}
	
	public double calAWT(int[] arr) 
	{
		double AWT = 0;
		for (int i = 0; i < arr.length; i++) 
		{
			AWT += arr[i];
		}
		AWT /= arr.length;
		return AWT;
	}
	
	public double calATAT(int[] arr) 
	{
		double ATAT = 0;
		for (int i = 0; i < arr.length; i++) 
		{
			ATAT += arr[i];
		}
		ATAT /= arr.length;
		return ATAT;
	}
	
	public ArrayList<runningProcess> getRP() 
	{
		return runningprocessQueue;
	}
	
	public double getATAT() 
	{
		return ATAT;
	}
	
	public double getAWT() 
	{
		return AWT;
	}
}