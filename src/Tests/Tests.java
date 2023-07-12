import static org.junit.Assert.*;
public class Tests {
    public static void main(String[] args)
    {
        int [][]tests={{6,5,4,3,2,1},{0,4,-2,-3,5,-1},{0},{0,0,0,0},{-1,0,1}};
        int [][]ans={{1,2,3,4,5,6},{-3,-2,-1,0,4,5},{0},{0,0,0,0},{-1,0,1}};
        HeapForTests a=new HeapForTests();
        for(int i=0;i<5;++i)
        {
            a.reHeap();
            for(int j=0;j<tests[i].length;++j)
            {
                a.Add(tests[i][j]);
            }
            a.heapSort();
            assertArrayEquals(a.getHeap(),ans[i]);
        }
    }
}
