import java.util.Comparator;

public class MyComparator implements Comparator<AGprocess> 
{	
	public int compare(process p1, process p2)
	{
		// TODO Auto-generated method stub
		return -1;
	} 
	
	public int compare(SJFprocess p1, SJFprocess p2) 
    { 
        if(p1.arrivalTime>p2.arrivalTime)
        {
        return 1;
        }
        else if(p1.arrivalTime<p2.arrivalTime){
        return -1;
        }
        return 0;
    }
	
	public int compare(AGprocess p1, AGprocess p2) 
    { 
        if(p1.AG_Factor>p2.AG_Factor)
        {
        return 1;
        }
        else if(p1.AG_Factor<p2.AG_Factor){
        return -1;
        }
        return 0;
    }
}
