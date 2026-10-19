public class ArrayStack<T> implements StackInterface<T> {
    private ArrayLog<T> stack;

    public ArrayStack() {
        stack = new ArrayLog<T>("Stack");
    }
    
    
    public int size() {
        return stack.size();
    }   
    
    // tests if this stack is empty
    public boolean empty() {
        return stack.isEmpty();
    }
    
    // looks at the object at the top of this stack
    // without removing it from the stack
    public T peek() throws StackUnderflowException {
        if(stack.size() == 0) {
            throw new StackUnderflowException("Tried to peek with no objects in the stack.");
        }
        else {
            return stack.get(stack.size()-1);
        }
    }
    
    // removes the object at the top of this stack 
    // and returns that object as the value of this function
    public T pop() throws StackUnderflowException {
        if(stack.size() == 0) {
            throw new StackUnderflowException("Tried to pop with no objects in the stack.");
        }
        else {
            T temp = stack.get(stack.size()-1);
            stack.remove(stack.size()-1);
            return temp;
        }
    }
    
    // pushes an item onto the top of this stack
    public T push(T item) {
        stack.add(item);
        return item;
    }
        
    // removes all of the elements from this stack
    public void clear() {
        stack.clear();
    }
    
    // returns the 1 based position where an object is on this stack
    // note: when the method ends, the stack is the same as it was at the start
    public int search(Object o) {
        int index = stack.indexOf((T)o);

        if(index == -1) {
            return -1;
        }
        else {
            return stack.size() - index;
        }
    }
}