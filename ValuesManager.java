import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ValuesManager
{
	private RandomAccessFile dataVAL;
	private long numRecords;

	public ValuesManager(String fileName) throws IOException
	{
		File tempFile = new File(fileName);
		boolean exist = tempFile.exists();

		dataVAL = new RandomAccessFile(fileName, "rwd");

		if (exist)
		{
			dataVAL.seek(0L);
			numRecords = dataVAL.readLong();
		}
		else
			numRecords = 0L;
	}

	public long insert(String value) throws IOException
	{
		int i = (byte)value.length();
		dataVAL.seek(numRecords * 256L + 8L);
		dataVAL.writeByte(i);
		dataVAL.writeBytes(value);

		numRecords += 1L;
    	dataVAL.seek(0L);
    	dataVAL.writeLong(numRecords);

    	return numRecords - 1L;
	}

	public void close() throws IOException
  	{
  		dataVAL.seek(0L);
	  	dataVAL.writeLong(numRecords);
    	dataVAL.close();
	}
}