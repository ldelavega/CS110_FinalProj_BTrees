/**
 * Class that handles the nodes of the B-Tree node
 * 
 * @author Jaymie Antonio, Luis IV de la Vega, Christian Limsui
 */
public class BTreeNode
{
  public static final int ORDER = 5;
  public static final int LENGTH = 3 * ORDER - 1;
  
  public static final int EMPTY = -1;

  private static final int START = 2;
  private static final int INCREMENT = 3;

  private long[] node;
  
  public BTreeNode(long[] bTree)
  {
    node = bTree;
  }
  
  /**
   * Checks if the current node is occupied
   * 
   * @return if node is full or not
   */
  public boolean getFull()
  {
    return node[LENGTH - START] != EMPTY;
  }
  
  /**
   * Get the node
   * 
   * @return node array
   */
  public long[] getNode()
  {
    return node;
  }
  
  /**
   * Get index of the node
   * 
   * @param key key of the node to get
   * @return contents of the node
   */
  public long getIndex(long key)
  {
    for(int i = START; i < LENGTH; i += INCREMENT)
    {
      // if the key value is the same, returns the index which is i+1 in long[] node
      if(key == node[i])
        return node[i + 1];
    }
    // else return EMPTY
    return EMPTY;
  }
  
  //insert the key and index
  /**
   * Insert the key and index
   * 
   * @param key key of the node to insert value in
   * @param index index of the node to insert value in
   */
  public void insert(long key, long index)
  {
    
    for(int i = START; i < LENGTH; i += INCREMENT)
    {
      long elem = node[i];
      //insert key and index if index is EMPTY
      if(node[i + 1] == EMPTY)
      {
        node[i] = key;
        node[i + 1] = index;
        break;
      }
      
      //sorts the node if elem is greater than key
      if(elem > key)
      {
        long tempKey1 = node[i];
        long tempIndex1 = node[i + 1];
        long tempX1 = node[i + 2];

        node[i] = key;
        node[i + 1] = index;

        i += INCREMENT;
        while(i < LENGTH && node[i + 1] != EMPTY)
        {
          long tempKey2 = node[i];
          long tempIndex2 = node[i + 1];
          long tempX2 = node[i + 2];

          node[i] = tempKey1;
          node[i + 1] = tempIndex1;
          node[i + 2] = tempX1;

          tempKey1 = tempKey2;
          tempIndex1 = tempIndex2;
          tempX1 = tempX2;

          i += INCREMENT;
        }

        if(i >= LENGTH)
          break;

        node[i] = tempKey1;
        node[i + 1] = tempIndex1;
        node[i + 2] = tempX1;
        break;
      }
    }
  }
}
