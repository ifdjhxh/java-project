public class Heap {
    enum States{
        COMPARE,
        CHANGE,
        DEFAULT
    }
    private States state = States.DEFAULT;
    private int[] heap=new int[0];
    private int last;
    private int index1 = -1;
    private int index2 = -1;
    private int index3 = -1;

    public int getIndex3() {
        return index3;
    }

    public States getState() {
        return state;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }

    public int getLast() {
        return last;
    }

    public Heap()
    {
        last=0;
    }
    public static int swap(int a,int b)
    {
        return a;
    }

    public int[] getHeap() {
        return heap;
    }


    public void sift_up(int current_i)
    {
        int next=current_i;
        while(next>-1)
        {
            current_i=next;
            next=-1;
            if(current_i==0)
                return;
            int parent_i=current_i/2;
//            System.out.println("Сравнение");
            if(heap[parent_i]<heap[current_i])//Сравниваем текущую вершину с её родителем. Если текущая>родитель,то меняем местами
            {
                heap[current_i]=swap(heap[parent_i], heap[parent_i]=heap[current_i]);
                next=parent_i;
                continue;
            }
        }
    }
    public void add(int current)
    {
        int []arr=new int[heap.length+1];
        for(int i=0;i<heap.length;++i)
            arr[i]=heap[i];
        arr[last]=current;
        heap=arr;
        sift_up(last);
        last++;
    }
    public void add_no_sift(int current){

        int []arr=new int[heap.length+1];
        for(int i=0;i<heap.length;++i)
            arr[i]=heap[i];
        arr[last]=current;
        heap=arr;
        last++;
    }

    public int sift_up_step(int current_i){
        int next = current_i;
        if (next>-1) {
            next = -1;
            if (current_i == 0) {
                return next;
            }
            int parent_i = (current_i-1) / 2;
            index1 = parent_i;
            index2 = current_i;
            System.out.println("Сравнение " + index1 + "  " + index2);
            state = States.COMPARE;
            if (heap[parent_i] < heap[current_i])//Сравниваем текущую вершину с её родителем. Если текущая>родитель,то меняем местами
            {
                state = States.CHANGE;
                heap[current_i] = swap(heap[parent_i], heap[parent_i] = heap[current_i]);
                next = parent_i;
            }
        }
        return next;
    }
    public int sift_down_step(int current_i){
        int next=current_i;
        if(next>-1)
        {
            state = States.DEFAULT;
            next=-1;
            int left;					//Значение левого ребёнка текущей вершины
            int rigth=-2147483648;				//Значение правого ребёнка текущей вершины
            int left_i=2*current_i+1;	//Индекс левого ребёнка
            int rigth_i=2*current_i+2;	//Индекс правого ребёнка
            int current=heap[current_i];//Значение текущей вершины

            if(left_i>=last)			//Если нет детей,то не просееваем
                return next;

            left=heap[left_i];

            if(rigth_i<last)
                rigth=heap[rigth_i];
            state = States.COMPARE;
            index1 = current_i;
            index2 = left_i;
            index3 = rigth_i;
            if(current>left && current>rigth)//Сравнение текущей вершины со всеми остальными
                return next;

            if(left>current && left>=rigth)	//Сравнение левого ребёнка со всеми остальными
            {
                state = States.CHANGE;
                heap[left_i]=swap(current,heap[current_i]=left);
                next=left_i;
                return next;
            }
            if(rigth>current && rigth>=left)//Сравнение правого ребёнка с остальными
            {
                state = States.CHANGE;
                heap[rigth_i]=swap(current,heap[current_i]=rigth);
                next=rigth_i;
                index2 = rigth_i;
                index3 = left_i;
                return next;
            }
        }
        return next;
    }
    public void sift_down(int current_i)
    {
        int next=current_i;
        while(next>-1)
        {
            current_i=next;				//Индекс текущей вершины
            next=-1;
            int left;					//Значение левого ребёнка текущей вершины
            int rigth=-1000000;				//Значение правого ребёнка текущей вершины
            int left_i=2*current_i+1;	//Индекс левого ребёнка
            int rigth_i=2*current_i+2;	//Индекс правого ребёнка
            int current=heap[current_i];//Значение текущей вершины

            if(left_i>=last)			//Если нет детей,то не просеиваем
                return;

            left=heap[left_i];

            if(rigth_i<last)
                rigth=heap[rigth_i];

            if(current>left && current>rigth)//Сравнение текущей вершины со всеми остальными
                return;

            if(left>current && left>=rigth)	//Сравнение левого ребёнка со всеми остальными
            {
                heap[left_i]=swap(current,heap[current_i]=left);
                next=left_i;
                continue;
            }
            if(rigth>current && rigth>=left)//Сравнение правого ребёнка с остальными
            {
                heap[rigth_i]=swap(current,heap[current_i]=rigth);
                next=rigth_i;
                continue;
            }
        }

    }

    public int heap_sort_start(){
        heap[last-1]=swap(heap[0],heap[0]=heap[last-1]);//добавление наибольшего элемента в конец массива и замена его на "последнего" листа
        --last;
        return heap[last];
    }
    public void heap_sort()
    {
        int temp=last;
        while(last>0)
        {
            --last;
            sift_down(0);

        }
        last=temp;
    }
    public void print()
    {
        for(int i=0;i<heap.length;++i)
        {
            System.out.printf("%d ",heap[i]);
        }
        System.out.printf("\n%d\n",last);
    }

}