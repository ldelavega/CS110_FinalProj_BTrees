import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BTreeManager
{
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
			data.seek(0L);
			numRecords = data.readLong();
			index = data.readLong();
		}
		else
		{
			numRecords = 0L;
			index = 0L;
		}
	}

	public void close() throws IOException
	{
		data.seek(0L);
		data.writeLong(numRecords);
		data.writeLong(index);
		data.close();
	}
}