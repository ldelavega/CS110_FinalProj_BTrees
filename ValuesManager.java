import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Class that handles the data.val file (Key-value pairs)
 * Handles the integer key-String value pairs, found in the data.val file
 * 
 * @author Jaymie Antonio, Luis IV de la Vega, Christian Limsui
 */
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
	
	/**
	 * getValue method. Called in the select method of the BTreeDB class. Returns the respective String value of the given key
	 * 
	 * @param key
	 * @return String associated with the key
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

	public void close() throws IOException
  	{
  		data.seek(0);
	  	data.writeLong(numRecords);
    	data.close();
	}
}
