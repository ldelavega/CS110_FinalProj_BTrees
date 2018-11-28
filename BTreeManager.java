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
	// for creating the B-Tree file
	private static final int LONG_SIZE = 8;
  	private static final int OFFSET_NUM_RECORDS = 0;
  	private static final int OFFSET_ROOT_INDEX = 8;
  	private static final int OFFSET_FIRST_NODE = 16;

	private RandomAccessFile data;
	private long numRecords;
	private long index;

	public BTreeManager(String fileName) throws IOException
	{
		File tempFile = new File(fileName);
		boolean exist = tempFile.exists();

		data = new RandomAccessFile(fileName, "rwd");

		if(exist)
		{
			data.seek(0);
			numRecords = data.readLong();
			index = data.readLong();
		}
		else
		{
			numRecords = 0;
			index = 0;

	`		data.writeLong(numRecords);
			data.writeLong(index);
			int size = BTreeNode.LENGTH;

			for(int i = 0; i < size; i++)
			{
				data.writeLong(-1);
			}
		}
	}

	private BTreeNode nodeMe(long key) throws IOException
	{
		data.seek(8);
		long x = data.readLong();

		int size = BTreeNode.LENGTH;
		long[] node = new long[size];

		for(int i = 0; i < size; i++)
		{
			node[i] = data.readLong();
		}

		return new BTreeNode(node);
	}

	private void writeNode(BTreeNode bTree, long x) throws IOException
	{
		long[] node = bTree.getNode();
		long seeker = 16 + x * 14 * 8;
		data.seek(seeker);

		for(int i = 0; i < 14; i++)
		{
			data.writeLong(node[i]);
		}
	}

	public void insert(long key, long index) throws IOException
	{
		BTreeNode bTree = nodeMe(key);

		bTree.insert(key, index);

		writeNode(bTree, 0);

		System.out.printf("%d inserted\n", key);
	}
	
	public static void select(long key) throws IOException
  	{
    		long selectKey = Long.valueOf(dataBT.getIndex(key));
	//might take out the L below
    		if (selectKey.longValue() == -1L) {
      			System.out.println("ERROR: " + selectKey + " does not exist.");
    		} else {
      			String keyVal = dataVAL.getValue(selectKey.longValue());
      			System.out.println(selectKey + " => " + keyVal);
    		}
  	}


	public long getIndex(long key) throws IOException
	{
		BTreeNode bTree = nodeMe(key);

		return bTree.getIndex(key);
	}
	
    public static void insert(long key, String value) throws IOException
    {
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
    }

	public void close() throws IOException
	{
		data.seek(0);
		data.writeLong(numRecords);
		data.writeLong(index);
		data.close();
	}
}
