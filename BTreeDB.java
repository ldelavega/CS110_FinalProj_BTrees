import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main class file for the B-Tree.
 * 
 * @author Jaymie Antonio, Luis IV de la Vega, Christian Limsui
 */
public class BTreeDB
{
	// Strings to be used as comparisons to the input from the user
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

    /**
     * Main method.
     * 
     * @param args arguements from the console, meant to refer to data.bt and data.val
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws IOException, FileNotFoundException
    {
        dataBT = new BTreeManager(args[0]);
        dataVAL = new ValuesManager(args[1]);
            
        Scanner sc = new Scanner(System.in);
        
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
                    sc.close();
                    break;
                }

                // second part of the input is the key
                long key = Long.valueOf(input[1]);
                // third part of the input is the value
                // unless the action is SELECT, in which case the value is EMPTY
                String value = input.length > 2 ? input[2] : EMPTY;

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
     * Insert method. Inserts a key and String value pair into the BTree
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
     * Update method. Updates the String value of an existing key. Otherwise, returns an error message of the key doesn't exist
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
     * Select method. Retrieves the index of the selected element, otherwise prints out an error message.
     * 
     * @param key key of the element being searched
     * @throws IOException 
    */
    public static void select(long key) throws IOException
    {
        // calls the getIndex method from the BTreeManager class
        long index = dataBT.getIndex(key);

        if (index == -1) {
            System.out.printf("ERROR: %d does not exist.\n", key);
        } else {
            String value = dataVAL.getValue(index);
            System.out.printf("%d => %d\n", key, value);
        }
    }
}
