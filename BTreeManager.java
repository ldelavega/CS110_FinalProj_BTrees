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
		}
	}

	public void close() throws IOException
	{
		data.seek(0);
		data.writeLong(numRecords);
		data.writeLong(index);
		data.close();
	}
}