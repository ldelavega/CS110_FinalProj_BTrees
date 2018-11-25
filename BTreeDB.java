import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BTreeDB
{
	public static final String EXIT = "exit";
	public static final String INSERT = "insert";
	public static final String SELECT = "select";
	public static final String UPDATE = "update";
	public static final String ERROR = "ERROR: invalid command";

    public static void main(String[] args) throws IOException, FileNotFoundException
    {
    	RandomAccessFile dataBT = new RandomAccessFile(args[0], "rwd");
		RandomAccessFile dataVAL = new RandomAccessFile(args[1], "rwd");
            
        Scanner sc = new Scanner(System.in);
        
        while(sc.hasNext())
        {
        	BTreeMe(sc.nextLine().split(" "), dataBT, dataVAL);
        }
    }

    public static void BTreeMe(String[] input, RandomAccessFile dataBT,  RandomAccessFile dataVAL)
    {
    	try
    	{
    		String action = input[0];

    		if (action.equals(EXIT))
    		{
    			dataBT.close();
    			dataVAL.close();
    			System.exit(0);
    		}

    		long key = Long.valueOf(input[1]);
    		String value = input.length > 2 ? input[2] : "";

    		switch (action)
            {
            	case INSERT:
        	        insert(key, value);
 	                break;
	            case SELECT:
	            	if(!value.equals(""))
	            		throw new Exception();
                    select(key);
            	    break;
            	case UPDATE:
       	            update(key, value);
                    break;
	            default:
                	throw new Exception();
            }
    	}
    	catch(Exception e)
    	{
    		System.out.println(ERROR);
    	}
    }
    
    public static void insert(long key, String value)
    {
        System.out.printf("--> in method insert( long key, String value ), with key = %d and value = %s\n", key, value);
    }

    public static void select(long key)
    {
        System.out.printf("--> in method select( long key, String value ), with key = %d\n", key);
    }

    public static void update(long key, String value)
    {
        System.out.printf("--> in method update( long key, String value ), with key = %d and value = %s\n", key, value);
    }
}