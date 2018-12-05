import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ValuesManager
{
	private static final String MODE = "rwd";
	private static final String UTF = "UTF8";

	private static final int HEADER_SIZE = 8;
	private static final int RECORD_SIZE = 256;

	private RandomAccessFile data;
	private long numRecords;

	public ValuesManager(String fileName) throws IOException
	{
		File tempFile = new File(fileName);
		boolean exist = tempFile.exists();

		data = new RandomAccessFile(fileName, MODE);

		if (exist)
		{
			data.seek(0);
			numRecords = data.readLong();
		}
		else
			numRecords = 0;
	}
	
	public String getValue(long key) throws IOException
	{
    	if (key >= numRecords)
    	{
      		return "";
    	}
    	else
    	{
    		data.seek(key * RECORD_SIZE + HEADER_SIZE);
    		int x = data.readByte();
    		byte[] valueByte = new byte[x];
    		data.read(valueByte);
    		return new String(valueByte, UTF);
		}
  	}

	public long insert(String value) throws IOException
	{
		int lengthByte = (byte) value.length();
		data.seek(numRecords * RECORD_SIZE + HEADER_SIZE);
		data.writeByte(lengthByte);
		data.writeBytes(value);

		numRecords += 1;
    	data.seek(0);
    	data.writeLong(numRecords);

    	return numRecords - 1;
	
        
        public void update(String value,long key) throws IOException
	{   
        long updateRec = key - 1;
		int lengthByte = (byte) value.length();
		data.seek(updateRec * RECORD_SIZE + HEADER_SIZE);
		data.writeByte(lengthByte);
		data.writeBytes(value);
	}

	public void close() throws IOException
  	{
  		data.seek(0);
	  	data.writeLong(numRecords);
    	data.close();
	}
}
