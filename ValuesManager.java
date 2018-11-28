import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ValuesManager {
	private static final int LONG_SIZE = 8;
  	private static final int OFFSET_NUM_RECORDS = 0;
  	private static final int OFFSET_START_OF_RECORDS = 8;
  	private static final int MAX_LENGTH = 255;
  	private static final int RECORD_LENGTH = 256;

	private RandomAccessFile data;
	private long numRecords;

	public ValuesManager(String fileName) throws IOException {
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
	
	public String getValue(long key) throws IOException {
    		if (key >= numRecords) {
      			return null;
    		} else {
    			data.seek(key * 256L + 8L);
    			int i = data.readByte();
    			byte[] arrayOfByte = new byte[i];
    			data.read(arrayOfByte);
    			return new String(arrayOfByte, "UTF8");
		}
  }

<<<<<<< HEAD
	public long insert(String value) throws IOException
	{
		int i = (byte) value.length();
		data.seek(numRecords * 256 + 8);
=======
	public long insert(String value) throws IOException {
		int i = (byte)value.length();
		data.seek(numRecords * 256L + 8L);
>>>>>>> ef84f467c9465f59ca44ac026d249a44a6eb4a66
		data.writeByte(i);
		data.writeBytes(value);

		numRecords += 1;
    	data.seek(0);
    	data.writeLong(numRecords);

    	return numRecords - 1;
	}

<<<<<<< HEAD
	public void close() throws IOException
  	{
  		data.seek(0);
=======
	public void close() throws IOException {
  		data.seek(0L);
>>>>>>> ef84f467c9465f59ca44ac026d249a44a6eb4a66
	  	data.writeLong(numRecords);
    	data.close();
	}
}
