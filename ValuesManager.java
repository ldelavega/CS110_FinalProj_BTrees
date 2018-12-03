import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ValuesManager
{
	private RandomAccessFile data;
	private long numRecords;

	public ValuesManager(String fileName) throws IOException
	{
		File tempFile = new File(fileName);
		boolean exist = tempFile.exists();

		data = new RandomAccessFile(fileName, "rwd");

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
    		data.seek(key * 256 + 8);
    		int x = data.readByte();
    		byte[] valueByte = new byte[x];
    		data.read(valueByte);
    		return new String(valueByte, "UTF8");
		}
  	}

	public long insert(String value) throws IOException
	{
		int lengthByte = (byte) value.length();
		data.seek(numRecords * 256 + 8);
		data.writeByte(lengthByte);
		data.writeBytes(value);

		numRecords += 1;
    	data.seek(0);
    	data.writeLong(numRecords);

    	return numRecords - 1;
	}
        
        public void update(String value,long key) throws IOException
	{   
                long updateRec = key - 1;
		int lengthByte = (byte) value.length();
		data.seek(updateRec * 256 + 8);
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
