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

	public BTreeManager(String fileName) throws IOException
	{
		File tempFile = new File(fileName);
		boolean exist = tempFile.exists();

		data = new RandomAccessFile(fileName, MODE);

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

			data.writeLong(numRecords);
			data.writeLong(index);
			int size = BTreeNode.LENGTH;

			for(int i = 0; i < size; i++)
			{
				data.writeLong(NULL);
			}
		}
	}

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

	public long getIndex(long key) throws IOException
	{
		BTreeNode bTree = nodeMe(key);

		return bTree.getIndex(key);
	}

	public void close() throws IOException
	{
		data.seek(0);
		data.writeLong(numRecords);
		data.writeLong(index);
		data.close();
	}
}

