import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

<<<<<<< HEAD
/**
 * Main class file for the B-Tree.
 * 
 * @author Jaymie Antonio, Luis IV de la Vega, Christian Limsui
 */
public class BTreeDB
{
    // inputs
=======
public class BTreeDB {
	// Strings to be used as comparisons to the input from the user
>>>>>>> ef84f467c9465f59ca44ac026d249a44a6eb4a66
	private static final String EXIT = "exit";
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
    private static final String SELECT = "select";
    // for the select method (empty string)
    private static final String EMPTY = "";
    // error statement
	private static final String ERROR = "ERROR: invalid command";

    // data.bt
    private static BTreeManager dataBT;
    // data.val
    private static ValuesManager dataVAL;

<<<<<<< HEAD
    /**
     * Main method.
     * 
     * @param args arguements from the console
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws IOException, FileNotFoundException
    {
=======
    public static void main(String[] args) throws IOException, FileNotFoundException {
>>>>>>> ef84f467c9465f59ca44ac026d249a44a6eb4a66
    	dataBT = new BTreeManager(args[0]);
		dataVAL = new ValuesManager(args[1]);
            
        Scanner sc = new Scanner(System.in);
        
<<<<<<< HEAD
        // loop until input is EXIT
        while(sc.hasNext())
        {
            // splits the input into an array
        	String[] input = sc.nextLine().split(" ");

            // when error is caught, print out ERROR statement
            try
            {
                // first part of the input is the action
                String action = input[0];

                // if the action is EXIT, close the files and end loop
                if (action.equals(EXIT))
                {
                    dataBT.close();
                    dataVAL.close();
                    break;
                }
=======
        while(sc.hasNext()) {
        	BTreeMe(sc.nextLine().split(" "));
        }
    }

    public static void BTreeMe(String[] input) {
    	try {
    		String action = input[0];

			// Terminate the program on command
    		if (action.equals(EXIT)) {
    			dataBT.close();
    			dataVAL.close();
    			System.exit(0);
    		}
>>>>>>> ef84f467c9465f59ca44ac026d249a44a6eb4a66

                // second part of the input is the key
                long key = Long.valueOf(input[1]);
                // third part of the input is the value
                // unless the action is SELECT, in which case the value is EMPTY
                String value = input.length > 2 ? input[2] : EMPTY;

<<<<<<< HEAD
                // switch cases for the action
                switch (action)
                {
                    // insert key and value to B-Tree
                    case INSERT:
                        insert(key, value);
                        break;
                    // update value of similar key in the B-Tree
                    case UPDATE:
                        update(key, value);
                        break;
                    // select the value of similar key
                    // if input has non-EMPTY value, throw exception
                    case SELECT:
                        if(!value.equals(EMPTY))
                            throw new Exception();
                        else
                            select(key);
                        break;
                    // if action is none of the inputs, throw exception
                    default:
                        throw new Exception();
                }
            }
            // catch all exception, then print ERROR
            catch(Exception e)
            {
                System.out.println(ERROR);
            }
        }
    }
    

    /**
     * Insert method.
     * 
     * @param key
     * @param value
     * @throws IOException 
     */
    public static void insert(long key, String value) throws IOException
    {
        long index = dataVAL.insert(value);
        dataBT.insert(key, index);
    }

    /**
     * Update method.
     * 
     * @param key
     * @param value
     * @throws IOException 
     */
    public static void update(long key, String value)
    {
        System.out.printf(" --> in method update( long key, String value ), with key = %d and value = %s\n", key, value);
    }

    /**
     * Select method.
     * 
     * @param key
     * @throws IOException 
     */
    public static void select(long key)
    {
	    dataBT.select(key);
    }
        //System.out.printf(" --> in method select( long key, String value ), with key = %d\n", key);
=======
			// Commands from the user
    		switch (action) {
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
    	} catch(Exception e) {
    		System.out.println(ERROR);
    	}
    }
    
    /**public static void insert(long key, String value) throws IOException {
	// If <key> already exists, print “ERROR: [key] already exists."
	Scanner keyScan =new Scanner("data.bt");
	while (keyScan.hasNextLine()) {
                final String valCopy = keyScan.nextLine();
                if(valCopy.contains(key)) {
                    System.out.println("ERROR: " + key + " already exists.");
                    break;
                }
	}
	    
        // Inserts a key associated to value, and then prints [key] inserted.
        long index = dataVAL.insert(value);
        System.out.printf(" --> in method insert( long key, String value ), value %s inserted at index %d\n", value, index);
		    
	// If [value] is omitted, insert an empty string
    }*/

    public static void select(long key)
    {
	    dataBT.select(key);
    }

    public static void update(long key, String value) {
        System.out.printf(" --> in method update( long key, String value ), with key = %d and value = %s\n", key, value);
>>>>>>> ef84f467c9465f59ca44ac026d249a44a6eb4a66
    }
}
