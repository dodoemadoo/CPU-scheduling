import java.util.ArrayList;
import java.util.Scanner;

import com.sun.org.apache.xpath.internal.operations.Bool;

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
	private int numOfProcess,quantum,time=0;
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
	    pQuantum = fill_Quantum(pQuantum, quantum);
	    npQuantum = fill_npQuantum(npQuantum, pQuantum);
	    while (numOfProcess!=0) 
	    {
	    	System.out.print("pQuantum: ");
	    	printArr(pQuantum);
	    	System.out.print("npQuantum: ");
	    	printArr(npQuantum);
		    readyprocessQueue = fillRQ(readyprocessQueue, processQueue, time);
			if (readyprocessQueue.size()!=0 || currProcess!=null) 
			{
				if (currProcess==null)
					currProcess = readyprocessQueue.remove(0);
				if (rp == null)
				{
					rp = new runningProcess();
					rp.startTime = time;
					rp.name = currProcess.processName;
				}
				int index = getProcessIndex(currProcess.processName, processQueue);
				if ( npQuantum[index] < currProcess.burstTime) 
				{
					time += npQuantum[index];
					processQueue[index].burstTime -= npQuantum[index];
					readyprocessQueue = fillRQ(readyprocessQueue, processQueue, time);
					if(getMinAG(readyprocessQueue,currProcess)!=null)
					{
						rp.endTime = time;
						runningprocessQueue.add(rp);
						rp = null;
						pQuantum[index] += (pQuantum[index]-npQuantum[index]);
						readyprocessQueue.add(currProcess);
						currProcess = getMinAG(readyprocessQueue, currProcess);
						readyprocessQueue.remove(currProcess);
					}
					else
					{
						boolean check = true;
						for (int i = npQuantum[index]+1; i < pQuantum[index]; i++) 
						{
							time++; 
							readyprocessQueue = fillRQ(readyprocessQueue, processQueue, time);
							processQueue[index].burstTime --;
							if(getMinAG(readyprocessQueue, currProcess) == null)
							{
								continue;
							}
							else
							{
								check = false;
								processQueue[index].burstTime -= (pQuantum[index]-i);
								pQuantum[index] += (pQuantum[index]-i);
								rp.endTime = time;
								runningprocessQueue.add(rp);
								rp = null;
								readyprocessQueue.add(currProcess);
								currProcess = getMinAG(readyprocessQueue, currProcess);
								readyprocessQueue.remove(currProcess);
								break;
							}
						}
						if(check)
						{
							currProcess = null;
							pQuantum[index] += getMeanQuantum(pQuantum);
						}
					}
				}
				else 
				{
					time += currProcess.burstTime;
					processQueue[index].burstTime = 0;
					pQuantum[index] = 0;
					npQuantum[index] = 0;
					rp.endTime = time;
					runningprocessQueue.add(rp);
					currProcess = null;
					numOfProcess--;
					rp = null;
				}
				fill_npQuantum(npQuantum, pQuantum);
			}
			else
				time++;
		}
	    for (int i = 0; i < runningprocessQueue.size(); i++) 
	    {
			System.out.println(runningprocessQueue.get(i).name+" "+runningprocessQueue.get(i).startTime+" "+runningprocessQueue.get(i).endTime);
		}
	}
	
	public static int[] fill_Quantum(int[] arr,int value)
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
	
	public static int getMeanQuantum(int[] arr) 
	{
		int mean = 0;
		for (int i = 0; i < arr.length; i++) 
		{
			mean += arr[i];
		}
		mean = (int)Math.ceil(mean*0.1);
		return mean;
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
	
	public static AGprocess getMinAG(ArrayList<AGprocess> rq, AGprocess curr)
	{
		boolean check = false;
		AGprocess minProcess = curr;
		for (int i = 0; i < rq.size(); i++)
		{
			if(minProcess.AG_Factor > rq.get(i).AG_Factor)
			{
				check = true;
				minProcess = rq.get(i);
			}
		}
		if(check)
			return minProcess;
		return null;
	}
	public static ArrayList<AGprocess> fillRQ(ArrayList<AGprocess> rq, AGprocess[] q,int t) 
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
