public class BTreeNode
{
  public static final int LENGTH = 3 * 5 - 1;
  public static final int EMPTY = -1;

  private static final int START = 2;
  private static final int INCREMENT = 3;

  private long[] node;

  public BTreeNode(long[] bTree)
  {
    node = bTree;
  }

  public boolean getFull()
  {
    return node[LENGTH - START] != EMPTY;
  }

  public long[] getNode()
  {
    return node;
  }

  public long getIndex(long key)
  {
    for(int i = START; i < LENGTH; i += INCREMENT)
    {
      if(key == node[i])
        return node[i + 1];
    }
    return EMPTY;
  }

  public void insert(long key, long index)
  {
    for(int i = START; i < LENGTH; i += INCREMENT)
    {
      long elem = node[i];

      if(node[i + 1] == EMPTY)
      {
        node[i] = key;
        node[i + 1] = index;
        break;
      }

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