import java.util.LinkedList;
import java.util.ListIterator;

class Map
{
    private String key;
    private int value;

    public Map(String key, Integer value)
    {
        this.key = key;
        this.value = value;
    }
    public String getKey()
    {
        return this.key;
    }
    public int getValue()
    {
        return this.value;
    }
}


class HashMap
{
    private LinkedList<Map> [] arr;
    private double loadFactor = 0.75;
    private int capacity = 7;
    private int size;

    public HashMap()
    {
        this.arr = new LinkedList[this.capacity];
        for(int i = 0; i < this.capacity; ++i)
        {
            this.arr[i] = new LinkedList<Map>();
        }
    }
    public int hash(String str)
    {
        int sum = 0;
        for(int i = 0; i < str.length(); ++i)
        {
            sum += str.charAt(i);
        }
        return (sum % this.capacity);
    }

    public boolean search(Map map)
    {
       return arr[hash(map.getKey())].contains(map);
    }

    public void insert(Map map)
    {
        //LinkedList<Map> list = this.arr[hash(map.getKey())];
        if(map == null)
        {
            throw new RuntimeException("Invalid");
        }
        if(!this.search(map))
        {
            this.arr[hash(map.getKey())].add(map);
            ++this.size;
            if((double)(this.size) / this.capacity > this.loadFactor)
            {
                this.rehash();
            }
        }
    }


    public void delete(Map map)
    {
        if(map == null)
        {
            throw new RuntimeException("Invalid");
        }
        if(!this.search(map))
        {
            throw new RuntimeException("Invalid");
        }
        this.arr[hash(map.getKey())].remove(map);
        --this.size;

        if((double) (this.size) / this.capacity < this.loadFactor)
        {
            this.shrink();
        }
    }



    private boolean isPrime(int number)
    {
        if(number <= 1)
        {
            return false;
        }
        for(int i = 2; (i * i) <= number; ++i)
        {
            if(number % i == 0)
            {
                return false;
            }
        }
        return true;
    }
    public void rehash()
    {
        int newCapacity = this.capacity;
        while(!isPrime(newCapacity))
        {
            ++newCapacity;
        }
        this.capacity = newCapacity;
        LinkedList<Map> [] arr1 = new LinkedList[newCapacity];
        for(int i = 0; i < newCapacity; ++i)
        {
            arr1[i] = new LinkedList<Map>();
        }

        for(int i = 0; i < this.arr.length; ++i)
        {
            for(int j = 0; j < this.arr[i].size(); ++j)
            {
                arr1[hash(this.arr[i].get(j).getKey())].add(this.arr[i].get(j));
            }
        }
        this.arr = arr1;
    }


    public void shrink()
    {
        int newCapacity = (int) Math.ceil(this.size / this.loadFactor);
        while(!isPrime(newCapacity))
        {
            ++newCapacity;
        }


        this.capacity = newCapacity;
        LinkedList<Map> arr1 [] = new LinkedList[newCapacity];
        for(int i = 0; i < newCapacity; ++i)
        {
            arr1[i] = new LinkedList<Map>();
        }
        for(int i = 0; i < this.arr.length; ++i)
        {
            for(int j = 0; j < this.arr[i].size(); ++j)
            {
                arr1[hash(this.arr[i].get(j).getKey())].add(this.arr[i].get(j));
            }
        }
        this.arr = arr1;
    }


    public void print() {
        for (int i = 0; i < this.arr.length; ++i) {
            System.out.print("Index " + i + ": ");
            for (Map map : this.arr[i]) {
                System.out.print("(" + map.getKey() + ", " + map.getValue() + ") ");
            }
            System.out.println();
        }
    }

}


public class Main
{
    public static void main(String[] args)
    {
        HashMap hashMap = new HashMap();
        Map deleteKey = new Map("key1", 1);
        Map searchKey = new Map("key2", 2);

        hashMap.insert(deleteKey);
        hashMap.insert(searchKey);
        hashMap.insert(new Map("key3", 3));

        boolean keyExists = hashMap.search(searchKey);
        System.out.println(keyExists);
        hashMap.print();

        hashMap.delete(deleteKey);
        hashMap.print();

        Map nonExistingKey = new Map("key4", 4);
        try {
            hashMap.delete(nonExistingKey);
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}