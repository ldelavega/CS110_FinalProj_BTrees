import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Class that handles the data.bt file (B-Tree).
 * 
 * @author Jaymie Antonio, Luis IV de la Vega, Christian Limsui
 */
public class BTreeManager
{
	private static final String MODE = "rwd";

	private static final int HEADER_SIZE = 8;
	private static final int RECORD_SIZE = 256;
	private static final int POINTERS = 14;
	private static final int ROOT_INDEX = 8;
	private static final int FIRST_NODE = 16;
	private static final int NULL = -1;

	private RandomAccessFile data;
	private long numRecords;
	private long index;

	/**
     * Constructor method.
     * 
     * @param fileName file name given for the B-Tree Manager
     * @throws IOException
     */
	public BTreeManager(String fileName) throws IOException
	{
		// boolean for checking if there is already a B-Tree Manager.
		File tempFile = new File(fileName);
		boolean exist = tempFile.exists();

		// Create RAF
		data = new RandomAccessFile(fileName, MODE);

		// if file exist, access the RAF
		if(exist)
		{
			data.seek(0);
			numRecords = data.readLong();
			index = data.readLong();
		}
		// if not, give values the B-Tree Manager.
		else
		{
			numRecords = 0;
			index = 0;

			data.writeLong(numRecords);
			data.writeLong(index);
			int size = BTreeNode.LENGTH;

			for(int i = 0; i < size; i++)
			{
				data.writeLong(NULL);
			}
		}
	}

	/**
     * Return the current B-Tree Node
     * 
     * @param key key given to access the B-Tree
     * @return B-Tree node.
     * @throws IOException
     */
	private BTreeNode nodeMe(long key) throws IOException
	{
		data.seek(HEADER_SIZE);
		long x = data.readLong();

		int size = BTreeNode.LENGTH;
		long[] node = new long[size];

		for(int i = 0; i < size; i++)
		{
			node[i] = data.readLong();
		}

		return new BTreeNode(node);
	}

	/**
     * Writes in the B-Tree Node
     * 
     * @param bTree B-Tree to be written on
     * @param x value to seek
     * @throws IOException
     */
	private void writeNode(BTreeNode bTree, long x) throws IOException
	{
		long[] node = bTree.getNode();
		long seeker = FIRST_NODE + x * POINTERS * ROOT_INDEX;
		data.seek(seeker);

		for(int i = 0; i < POINTERS; i++)
		{
			data.writeLong(node[i]);
		}
	}

	/**
     * Insert method.
     * 
     * @param key key of the value
     * @param value index of the value
     * @throws IOException 
     */
	public void insert(long key, long index) throws IOException
	{
		BTreeNode bTree = nodeMe(key);

		// If <key> already exists, print â€œERROR: [key] already exists."
		if(bTree.getIndex(key) != bTree.EMPTY)
		{
			System.out.printf("ERROR: %d already exist.\n", key);
		}
		// Inserts a key associated to value, and then prints [key] inserted.
		else
		{
			bTree.insert(key, index);
			writeNode(bTree, 0);

			System.out.printf("%d inserted\n", key);
		}
		// If [value] is omitted, insert an empty string
	}
    
    /**
     * Update method.
     * 
     * @param key key of the value
     * @return if key exist or not
     * @throws IOException 
     */
    public boolean update(long key) throws IOException
	{
		BTreeNode bTree = nodeMe(key);
        boolean canSwap = false;
                

		if(bTree.getIndex(key) == bTree.EMPTY)
		{
			System.out.printf("ERROR: %d does not exist.\n", key);
		}
		else
		{
        	canSwap = true;
		}
		
        return canSwap;
	}

	/**
     * Method to return the index
     * 
     * @param key key of the value
     * @return index
     * @throws IOException 
     */
	public long getIndex(long key) throws IOException
	{
		BTreeNode bTree = nodeMe(key);

		return bTree.getIndex(key);
	}

	/**
     * Close the B-Tree Manager file
     * 
     * @return index
     * @throws IOException 
     */
	public void close() throws IOException
	{
		data.seek(0);
		data.writeLong(numRecords);
		data.writeLong(index);
		data.close();
	}
}

