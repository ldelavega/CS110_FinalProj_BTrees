import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BTreeManager
{
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

			data.writeLong(numRecords);
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