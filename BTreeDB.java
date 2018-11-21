import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.util.Scanner;

public class BTreeDB
{
    public static void main(String[] args)
    {
        try
        {
            RandomAccessFile dataBT = new RandomAccessFile(args[0], "rwd");
            RandomAccessFile dataVAL = new RandomAccessFile(args[1], "rwd");
            System.out.println("//RAF opened.");
            
            Scanner sc = new Scanner(System.in);
        
            while(sc.hasNext())
            {
                String[] input = sc.nextLine().split("");

                if(input[0].equals("insert"))
                {
                    insert(input, dataBT);
                }
                else if(input[0].equals("exit"))
                {
                    break;
                }
            }
        }
        
        catch (IOException e)
        {
            System.out.println("IOException at main method.");
        }
        
        
    }
    
    public static void insert(String[] input, RandomAccessFile dataBT)
    {
        
    }
}