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
	
    public static void insert(long key, String value) throws IOException
    {
	// If <key> already exists, print “ERROR: [key] already exists."
	Scanner keyScan =new Scanner("data.bt");
	while (keyScan.hasNextLine()) {
                final String valCopy = keyScan.nextLine();
                if(valCopy.contains(key)) {
                    System.out.println("ERROR: " + key + " already exists.");
                    break;
                }
	}
	    
        // Inserts a key associated to value, and then prints [key] inserted.
        long index = dataVAL.insert(value);
        System.out.printf(" --> in method insert( long key, String value ), value %s inserted at index %d\n", value, index);
		    
	// If [value] is omitted, insert an empty string
    }

	public void close() throws IOException
	{
		data.seek(0L);
		data.writeLong(numRecords);
		data.writeLong(index);
		data.close();
	}
}
