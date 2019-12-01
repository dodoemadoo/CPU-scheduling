import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class The_Comparator implements Comparator<Process> { 
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
class Process
{
	String ProcessName;
	String ProcessColor;
	int ArrivalTime;
	int BurstTime;
	int waitingTime;
	int tournarTime;
	boolean visited;
}

public class shortestJobFirst {
    PriorityQueue<Process> queue;
    shortestJobFirst(){
    Scanner s=new Scanner(System.in); 
    int numofprocess,At,Bt;  
    String name,colour;
    System.out.println("Enter the number of Processes :");
    numofprocess=s.nextInt();
    PriorityQueue<Process> queue = new PriorityQueue<Process>(numofprocess,new The_Comparator());
    for(int i=0;i<numofprocess;i++){
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
    p.visited=false;
    queue.add(p);
    }      
List<Integer>res=makechart(queue);
        System.out.println("The Process order is..");
        Iterator value = queue.iterator();
        int avWaiting=0,avtounar=0;
        for(int i=0;i<res.size();i++){
           while(value.hasNext()){
               Process nex=(Process) value.next(); 
              if(i!=res.size()-1){ 
              if(res.get(i+1)-res.get(i)==nex.BurstTime && nex.visited==false){
                  nex.waitingTime=res.get(i)-nex.ArrivalTime;
                  nex.tournarTime=nex.waitingTime+nex.BurstTime;
                  System.out.println(nex.ProcessName+" "+nex.waitingTime+" "+nex.tournarTime);
                  nex.visited=true;
                  avWaiting+=nex.waitingTime;
                  avtounar+=nex.tournarTime;
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
public List makechart(PriorityQueue<Process> q){
List <Integer>chart=new ArrayList<Integer>();    
int [][]temp = new int[q.size()][2];
Iterator value = q.iterator();  
 for(int i=0;i<q.size();i++){
     Process nex=(Process) value.next();
       for(int j=0;j<2;j++){
           if(j==0)
           temp[i][j]=nex.ArrivalTime;
           else
             temp[i][j]=nex.BurstTime;           
 }
 }
 if(all(temp)){
 chart.add(temp[0][0]);
 chart.add(temp[0][1]);
List <Integer>temp2=new ArrayList<Integer>(); 
 for(int i=1;i<temp.length;i++){
         for(int j=0;j<2;j++){
             if(j==1)
         temp2.add(temp[i][j]);
         }
 }
 Collections.sort(temp2);
 for(int i=0;i<temp2.size();i++){
    
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