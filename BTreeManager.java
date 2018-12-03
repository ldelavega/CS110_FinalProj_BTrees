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
				data.writeLong(-1);
			}
		}
	}

	/**
	 * nodeMe method, called in the getIndex method of this class.
	 * Creates a node to act as a medium between data.bt and this class
	 * 
	 * @param key
	 * @return Representation of the selected BTree node
	 * @throws IOException
	 */
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

	// called in the insert method of BTree class
	public void insert(long key, long index) throws IOException
	{
		BTreeNode bTree = nodeMe(key);

		// If <key> already exists, print “ERROR: [key] already exists."
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
	 * getIndex method, called in the select method of the BTreeDB class.
	 * Returns the index of the key within the structure of the BTree
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
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
