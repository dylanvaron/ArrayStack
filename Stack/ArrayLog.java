
/**
 * This ArrayLog object represents a Log ADT implemented as
 * a generic data type array using the EnhancedLogInterface.
 * 
 * @author  
 * @version 
 */
@SuppressWarnings("unchecked")  // eliminates compiler warnings from cast below
public class ArrayLog<T> implements EnhancedLogInterface<T>
{
    // Instance variables
    private T[] log;
    private String name;
    private int size;
    
    // Create a new String array with a capacity of 4 elements
    // and assign values to instance variables.
    public ArrayLog(String name)
    {
        // cannot create a generic array object, so has to be cast
        // from an Object back into the generic in order to compile
        this.log = (T[])new Object[4];
        this.name = name;
        this.size = 0;
    }

    // Returns the name of this StringLog.
    public String getName()
    {
        return name;
    }

    // Returns the logical size of this StringLog.
    public int size()
    {
        return size;
    }
    
    // Returns true if this list contains no elements.
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    // Returns true if this list is completely full.
    public boolean isFull()
    {
        return this.size == this.log.length;
    }

    // Appends the specified element to the end of this list.
    public void add(T element)
    {
        if(size == log.length) {
            T[] temp = log;
            log = (T[])new Object[size*2];
            for(int i = 0; i < size; i++) {
                log[i] = temp[i];
            }
        }

        log[size++] = element;
    }
  
    // Returns the element at the specified position in this list.
    public T get(int index)
    {   
        if(index >= 0 && index < size) {
            return log[index];
        }
        else {
            throw new ArrayIndexOutOfBoundsException("There is an Index Out of Bounds Exception from the get method.");
        }
    }
    
    // Returns the index of the first occurance of the specified element
    // in this list, or -1 if this list does not contain the element.
    public int indexOf(T element)
    {
        for(int i = 0; i < size; i++) {
            if(element.equals(log[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    // Returns true if this list contains the specified element.
    public boolean contains(T element)
    {
        for(int i = 0; i < size; i++) {
            if(element.equals(log[i])) {
                return true;
            }
        }

        return false;
    }
    
    // Returns a formatted string representation of this StringLog.
    public String toString()
    {
        String result = "Log: " + name + "\n";
        for (int i = 0; i < size; i++)
        {
            result += (i + 1) + ". " + log[i] + "\n";
        }
        return result;
    }
    
    // Replaces the element at the specified position in this list
    // with the specified element.  Returns what was at that location
    public T set(int index, T element)
    {
        T oldT = get(index);
        log[index] = element;
        return oldT;
    }
    
    // Inserts the specified element at the specified position in this list.
    public void add(int index, T element)
    {

        if(index >= 0 && index <= size) {

            if(size == log.length) {
                T[] temp = log;
                log = (T[])new Object[size*2];
                for(int i = 0; i < size; i++) {
                    log[i] = temp[i];
                }
            }

            for(int i = size; i > index; i--) {
                log[i] = log[i-1];
            }

            log[index] = element;
            size++;
        }
        else {
            throw new ArrayIndexOutOfBoundsException("There is an Index Out of Bounds Exception from the add(int, T) method.");
        }
    }
    
    // Removes the element at the specified position in this list, and
    // returns the element that was removed.  Any unused array elements
    // are set to null.
    public T remove(int index)
    {

        if(index >= 0 && index < size) {
            T oldT = get(index);
            log[index] = null;
            for(int i = index; i < size-1; i++) {
                log[i] = log[i+1];
            }
            log[size-1] = null;
            size--;

            int maxCapacity = Math.max(4, log.length / 2);
            if(size <= maxCapacity) {
                T[] temp = log;
                
                log = (T[])new Object[maxCapacity];
                for(int i = 0; i < size; i++) {
                    log[i] = temp[i];
                }
            }

            return oldT;
        }
        else {
            throw new ArrayIndexOutOfBoundsException("There is an Index Out of Bounds Exception from the remove method.");
        }
    }
    
    // Removes the first occurance of the specified element from this
    // list, if it is present.  Returns true if element was found (and 
    // removed), false otherwise.
    public boolean remove(T element)
    {
        int index = indexOf(element);
        if(index == -1) {
            return false;
        }

        remove(index);
        return true;
    }
    
    // Removes all of the elements from this list.
    public void clear()
    {
        log = (T[])new Object[4];
        size = 0;
    }
}