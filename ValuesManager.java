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
			data.seek(0L);
			numRecords = data.readLong();
		}
		else
			numRecords = 0L;
	}

	public long insert(String value) throws IOException {
		int i = (byte)value.length();
		data.seek(numRecords * 256L + 8L);
		data.writeByte(i);
		data.writeBytes(value);

		numRecords += 1L;
    	data.seek(0L);
    	data.writeLong(numRecords);

    	return numRecords - 1L;
	}

	public void close() throws IOException {
  		data.seek(0L);
	  	data.writeLong(numRecords);
    	data.close();
	}
}