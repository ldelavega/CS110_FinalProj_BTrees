import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BTreeDB
{
	private static final String EXIT = "exit";
	private static final String INSERT = "insert";
	private static final String SELECT = "select";
	private static final String UPDATE = "update";
    private static final String EMPTY = "";
	private static final String ERROR = "ERROR: invalid command";

    private static BTreeManager dataBT;
    private static ValuesManager dataVAL;

    public static void main(String[] args) throws IOException, FileNotFoundException
    {
    	dataBT = new BTreeManager(args[0]);
		dataVAL = new ValuesManager(args[1]);
            
        Scanner sc = new Scanner(System.in);
        
        while(sc.hasNext())
        {
        	BTreeMe(sc.nextLine().split(" "));
        }
    }

    public static void BTreeMe(String[] input)
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
    		String value = input.length > 2 ? input[2] : EMPTY;

    		switch (action)
            {
            	case INSERT:
        	        insert(key, value);
 	                break;
	            case SELECT:
	            	if(!value.equals(EMPTY))
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
    
    public static void insert(long key, String value) throws IOException
    {
	// If <key> already exists, print “ERROR: [key] already exists."
	Scanner keyScan =new Scanner("data.bt");
	while (keyScan.hasNextLine())
            {
                final String valCopy = keyScan.nextLine();
                if(valCopy.contains(key))
                {
                    System.out.println("ERROR: " + key " already exists.");
                    break;
                }
	    
        // Inserts a key associated to value, and then prints [key] inserted.
        long index = dataVAL.insert(value);
        System.out.printf(" --> in method insert( long key, String value ), value %s inserted at index %d\n", value, index);
		    
	// If [value] is omitted, insert an empty string
    }

    public static void select(long key)
    {
        System.out.printf(" --> in method select( long key, String value ), with key = %d\n", key);
    }

    public static void update(long key, String value)
    {
        System.out.printf(" --> in method update( long key, String value ), with key = %d and value = %s\n", key, value);
    }
}
