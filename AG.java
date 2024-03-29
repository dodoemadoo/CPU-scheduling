import java.util.ArrayList;
import java.util.Scanner;



public class AG 
{
	private Scanner scan= new Scanner(System.in);
	private ArrayList<AGprocess> readyprocessQueue = new ArrayList<AGprocess>();
	private ArrayList<runningProcess>runningprocessQueue = new ArrayList<runningProcess>();
	private AGprocess[] processQueue;
	private int[] npQuantum,pQuantum,TAT,WT;
	private AGprocess currProcess = null;
	private runningProcess rp = null;
	private int numOfProcess,quantum,time=0;
	double AWT,ATAT;
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
		    System.out.print("Enter the arrival time: ");
		    processQueue[i].arrivalTime = scan.nextInt();
		    System.out.print("Enter the burst Time: ");
		    processQueue[i].burstTime = scan.nextInt();
		    System.out.print("Enter the priority: ");
		    processQueue[i].cpBrustTime = processQueue[i].burstTime;
		    processQueue[i].priority = scan.nextInt();
		    processQueue[i].AG_Factor = processQueue[i].priority + processQueue[i].arrivalTime + processQueue[i].burstTime;
	    }  
	    npQuantum = new int[numOfProcess];
	    pQuantum = new int[numOfProcess];
	    TAT = new int[numOfProcess];
	    WT = new int[numOfProcess];
	    pQuantum = fill_Quantum(pQuantum, quantum);
	    npQuantum = fill_npQuantum(npQuantum, pQuantum);
	    while (numOfProcess!=0) 
	    {
	    	System.out.print("pQuantum: ");
	    	printArr(pQuantum);
	    	System.out.print("npQuantum: ");
	    	printArr(npQuantum);
		    System.out.println("--------------------------------------------------------------");
	    	readyprocessQueue = fillRQ(readyprocessQueue, processQueue, time,currProcess);
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
					readyprocessQueue = fillRQ(readyprocessQueue, processQueue, time,currProcess);
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
						for (int i = npQuantum[index]+1; i <= pQuantum[index]; i++) 
						{
							++time;
							readyprocessQueue = fillRQ(readyprocessQueue, processQueue, time , currProcess);
							processQueue[index].burstTime --;
							if(processQueue[index].burstTime==0)
							{
								check=false;
								processQueue[index].burstTime = 0;
								pQuantum[index] = 0;
								npQuantum[index] = 0;
								rp.endTime = time;
								runningprocessQueue.add(rp);
								currProcess = null;
								numOfProcess--;
								rp = null;
								break;
							}
							if(getMinAG(readyprocessQueue, currProcess) == null)
							{
								continue;
							}
							else
							{
								check = false;
								processQueue[index].burstTime -= (pQuantum[index]-i-1);
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
							readyprocessQueue.add(currProcess);
							currProcess = null;
							rp.endTime = time;
							runningprocessQueue.add(rp);
							rp = null;
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
	    System.out.print("pQuantum: ");
    	printArr(pQuantum);
    	System.out.print("npQuantum: ");
    	printArr(npQuantum);
	    System.out.println("--------------------------------------------------------------");
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
		mean /= arr.length;
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
	
	public static ArrayList<AGprocess> fillRQ(ArrayList<AGprocess> rq, AGprocess[] q,int t, AGprocess curr) 
	{
		for (int i = 0; i < q.length; i++)
		{
			if (q[i].arrivalTime <= t && !rq.contains(q[i]) && q[i].burstTime!=0 && q[i]!=curr)
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
	
	public static runningProcess getIndex(String key ,ArrayList<runningProcess>RP) 
	{
		for (int i = 0; i < RP.size(); i++)
		{
			if (key.equals(RP.get(i).name))
				return RP.get(i);
		}
		return null;
	}
	
	public static int getLastIndex(String key ,ArrayList<runningProcess>RP)
	{
		int index = -1;
		for (int i = RP.size()-1; i >= 0; i--) 
		{
			if (key.equals(RP.get(i).name))
			{
				return i;
			}
		}
		return index;
	}
	
	public int[] calTAT(int[] arr,ArrayList<runningProcess>RP, AGprocess[] QP) 
	{
		for (int i = 0; i < arr.length; i++)
		{
			runningProcess rp = getIndex(QP[i].processName, RP);
			int LI = getLastIndex(QP[i].processName, RP);
			arr[i] = RP.get(LI).endTime - QP[i].arrivalTime;
		}
		return arr;
	}
	
	public int[] calWT(int[] arr, AGprocess[] QP,int[] TAT) 
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
