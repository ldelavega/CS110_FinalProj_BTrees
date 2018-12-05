import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Class that handles the data.val file (Values of the B-Tree).
 * 
 * @author Jaymie Antonio, Luis IV de la Vega, Christian Limsui
 */
public class ValuesManager
{
	private static final String MODE = "rwd"; // Opens up file to reading and writing
	private static final String UTF = "UTF8"; // Character encoding, using 8 blocks to represent a character

	private static final int HEADER_SIZE = 8;	// Only 8 bytes for long integers
	private static final int RECORD_SIZE = 256; // Up to 255 characters for values of Strings

	private RandomAccessFile data; // refers to the file to be used for sotring kay-value pairs
	private long numRecords; // Number of key-value pairs recorded within the file

	/**
     * Constructor method.
     * 
     * @param fileName file name given for the Values Manager
     * @throws IOException
     */
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

	/**
     * Return the value associated with the key
     * 
     * @param key key of the value
     * @return value
     * @throws IOException
     */
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

  	/**
     * Insert method.
     * 
     * @param value value to be inserted
     * @return index of the value
     * @throws IOException
     */
	public long insert(String value) throws IOException
	{
		int lengthByte = (byte) value.length();
		data.seek(numRecords * RECORD_SIZE + HEADER_SIZE);
		data.writeByte(lengthByte);
		data.write(value.getBytes(UTF));

		numRecords += 1;
    	data.seek(0);
    	data.writeLong(numRecords);

    	return numRecords - 1;
    }
	
    /**
     * Update method.
     * 
     * @param value value to be updated
     * @param index index of the value to be updated
     * @throws IOException
     */    
	public void update(String value,long index) throws IOException
	{   
		int lengthByte = (byte) value.length();
        data.seek(index * RECORD_SIZE + HEADER_SIZE);
    
        data.writeByte(lengthByte);
        data.write(value.getBytes(UTF));
	}

	/**
     * Close the Values Manager file
     * 
     * @return index
     * @throws IOException 
     */
	public void close() throws IOException
  	{
  		data.seek(0);
	  	data.writeLong(numRecords);
    	data.close();
	}
}
