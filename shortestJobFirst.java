import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class shortestJobFirst 
{
    PriorityQueue<process> queue;
	private Scanner scan= new Scanner(System.in);
	private int numOfprocess;
    shortestJobFirst()
    {
	    System.out.print("Enter the number of processes: ");
	    numOfprocess=scan.nextInt();
	    PriorityQueue<process> queue = new PriorityQueue<process>(numOfprocess,new MyComparator());
	    for(int i=0;i<numOfprocess;i++)
	    {
		    process p=new SJFprocess();
		    System.out.print("Enter the name: ");
		    p.processName=scan.nextLine();  
		    System.out.print("Enter the color: ");
		    p.processColor=scan.nextLine();
		    System.out.print("Enter the Arrival time: ");
		    p.arrivalTime=scan.nextInt();
		    System.out.print("Enter the Burst Time: ");
		    p.burstTime=scan.nextInt();
		    //p.visited=false;
		    queue.add(p);
	    }      
	    List<Integer>res=makechart(queue);
        System.out.println("The process order is..");
        Iterator<process> value = queue.iterator();
        int avWaiting=0,avtounar=0;
        for(int i=0;i<res.size();i++){
           while(value.hasNext()){
               process nex = value.next(); 
              if(i!=res.size()-1){ 
              //if(res.get(i+1)-res.get(i)==nex.burstTime && nex.visited==false){
            	  if(res.get(i+1)-res.get(i)==nex.burstTime) {  
            	  nex.waitingTime=res.get(i)-nex.arrivalTime;
                  //nex.tournarTime=nex.waitingTime+nex.burstTime;
                  //System.out.println(nex.processName+" "+nex.waitingTime+" "+nex.tournarTime);
                  System.out.println(nex.processName+" "+nex.waitingTime);
                  //nex.visited=true;
                  avWaiting+=nex.waitingTime;
                  //avtounar+=nex.tournarTime;
                  value=queue.iterator();
                  break;
              }
              else{
              continue;
              }
              }
           }
        }
        System.out.println("The Average waiting time = "+avWaiting/queue.size());
        System.out.println("The Average Tournar time = "+avtounar/queue.size());
    }
	public List<Integer> makechart(PriorityQueue<process> q)
	{
		List <Integer>chart=new ArrayList<Integer>();    
		int [][]temp = new int[q.size()][2];
		Iterator<process> value = q.iterator();  
		for(int i=0;i<q.size();i++)
		{
		     process nex=(process) value.next();
		     for(int j=0;j<2;j++)
		     {
		    	 if(j==0)
		    		 temp[i][j]=nex.arrivalTime;
		         else
		        	 temp[i][j]=nex.burstTime;           
		     }
		}
		if(all(temp))
		{
			chart.add(temp[0][0]);
			chart.add(temp[0][1]);
			List <Integer>temp2=new ArrayList<Integer>(); 
			for(int i=1;i<temp.length;i++)
			{
				for(int j=0;j<2;j++)
				{
					if(j==1)
						temp2.add(temp[i][j]);
			    }
			}
			Collections.sort(temp2);
			for(int i=0;i<temp2.size();i++)
			{
				chart.add(temp2.get(i)+chart.get(chart.size()-1)); 
			}
			return chart;
			}
	 else{
	 }            
	return chart;
	}
	boolean all(int [][]arr){
	 int index=arr[0][1];   
	 for(int i=0;i<arr.length;i++){
	       for(int j=0;j<2;j++){
	           if(j==0){
	           if(index<arr[i][j]){
	               return false;
	           }
	               }
	 }
	 }
	return true;
	}
}