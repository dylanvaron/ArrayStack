import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class tests both the ArrayStack and LinkedStack classes
 * 
 * @author Mr. Bredemeier
 * @version October 7, 2012
 */

public class StackTest<T>
{
    private static boolean failed = false;
    private static boolean arrayFailed, linkedFailed;
    private static ArrayList<String> methodNames = new ArrayList<String>();
    private static StackInterface<Integer> stack;
    private static String message;
    private static String methodName;
    private static Class<?> c;
    private static boolean display = true;  // turns error display messages on or off

    public static void main(String [] args) throws StackUnderflowException
    {
        testInterface();
        testStack("ArrayStack");
        arrayFailed = failed;

        failed = false;
        testStack("LinkedStack");     
        linkedFailed = failed;
        
        if(arrayFailed /*|| linkedFailed*/)
            System.out.println("Bummer, you have not finished the Stack assignment.  Try again.");
        else
            System.out.println("Fantabulous!  You have completed the Stack assignment");
    }
    
    private static void testStack(String type) throws StackUnderflowException
    {
        stackTest(type);
        if(failed)
            System.out.println(type + " class does not pass the test\n");
    }
    
    private static void testInterface()
    {
        // make sure that StackInterface<T> is untouched
        try 
        {
            c = Class.forName("StackInterface");
            Member[] methods = c.getMethods();
            for (Member method : methods)
                methodNames.add(((Method)method).toGenericString());
        }
        catch(ClassNotFoundException e)
        {
            failure("Epic Failure: missing interface StackInterface");
            return;
        }

        methodName = "public abstract void StackInterface.clear()";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);
        
        methodName = "public abstract int StackInterface.size()";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract T StackInterface.pop() throws StackUnderflowException";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract T StackInterface.push(T)";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract T StackInterface.peek() throws StackUnderflowException";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract boolean StackInterface.empty()";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract int StackInterface.search(java.lang.Object)";
        if(!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        if(failed)
        {
            System.out.println("\nCorrupted interface StackInterface");
        }
    }

    private static void stackTest(String type) throws StackUnderflowException
    {
        //********** Generic Stack Class Test **************************************
        System.out.println("Now testing your " + type + " class:");
        try 
        {
            if(type.equals("ArrayStack"))
                stack = new ArrayStack<Integer>();
            else
                stack = new LinkedStack<Integer>();
            c = Class.forName(type);
        }
        catch(ClassNotFoundException e)
        {
            failure("Epic Failure: missing " + type + " class");
            return;
        }
        catch(NoClassDefFoundError e)
        {
            failure("Epic Failure: missing " + type + " class");
            return;
        }
        catch (NoSuchMethodError e)
        {
            failure("Failed: missing constructor " + type + "()");
            return;
        }        
        
        //make sure that ArrayStack implements StackInterface
        if(!(stack instanceof StackInterface))
        {
            failure("Failed: " + type + " does not implement StackInterface");
            return;
        }
        display(type + " implements StackInterface test");

        //validate size method
        if (stack.size() != 0)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (!stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
        
        // test push(element) method
        if(stack.push(new Integer(1)).intValue() != 1)
            failure("push(element) method is not correct");
            
        // validate size method
        if (stack.size() != 1)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
            
        // test peek method
        if(stack.peek().intValue() != 1)
            failure("peek method is not correct");
        
        // test pop method
        if(stack.pop().intValue() != 1)
            failure("pop method is not correct");
            
        // validate size method
        if (stack.size() != 0)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (!stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
        
        //validate pop throwing StackUnderflowException
        try
        {
            if(stack.pop() != null)
                failure("pop method is not correct");
        }
        catch (StackUnderflowException e)
        {
            display("pop method correctly throws StackUnderflowException");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            failure("pop method does not correctly throw StackUnderflowException");
        }
            
        //validate peek throwing StackUnderflowException
        try
        {
            if(stack.peek() != null)
                failure("peek method is not correct");
        }
        catch (StackUnderflowException e)
        {
            display("peek method correctly throws StackUnderflowException");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            failure("peek method does not correctly throw StackUnderflowException");
        }
        
        for(int i = 10; i <= 1000; i += 10)
        {
            try
            {
                stack.push(new Integer(i));
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                failure("array does not dynamically expand");
                break;
            }
        }
        
        // validate size method
        if (stack.size() != 100)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
        
        int x = stack.peek().intValue();
        if(x != 1000)
            failure("push and/or peek methods are incorrect");
        
        while(x > 10)
        {
            if(x != stack.pop().intValue())
                failure("push and/or pop methods are incorrect");
            x -= 10;
            if(x != stack.peek().intValue())
                failure("push and/or peek methods are incorrect");
        }

        // validate size method
        if (stack.size() != 1)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
        
        // test peek method
        if(stack.peek().intValue() != 10)
            failure("peek method is not correct");
        
        // test pop method
        if(stack.pop().intValue() != 10)
            failure("pop method is not correct");
            
        // validate size method
        if (stack.size() != 0)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (!stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
        
        //validate pop throwing StackUnderflowException
        try
        {
            if(stack.pop() != null)
                failure("pop method is not correct");
        }
        catch (StackUnderflowException e)
        {
            display("pop method again correctly throws StackUnderflowException");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            failure("pop method does not correctly throw StackUnderflowException");
        }
            
        //validate peek throwing StackUnderflowException
        try
        {
            if(stack.peek() != null)
                failure("peek method is not correct");
        }
        catch (StackUnderflowException e)
        {
            display("peek method again correctly throws StackUnderflowException");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            failure("peek method does not correctly throw StackUnderflowException");
        }
        
        if(!failed)
        {
            display("size method is correct");
            display("empty method is correct");
            display("push method is correct");
            display("peek method is correct");
            display("pop method is correct");
        }
        
        for(int i = 10; i > 0; i--)
        {
            stack.push(new Integer(i));
        }
        
        if(stack.search(new Integer(3)) != 3)
            failure("search method is incorrect");
        
        if(stack.search(new Integer(8)) != 8)
            failure("search method is incorrect");
        
        if(stack.search(new Integer(1)) != 1)
            failure("search method is incorrect");
        
        if(stack.search(new Integer(10)) != 10)
            failure("search method is incorrect");
        
        if(stack.search(new Integer(12)) != -1)
            failure("search method is incorrect");
            
        for(int i = 1; i <= 10; i++)
        {
            if (stack.pop().intValue() != i)
            {
                failure("search method did not restore stack to original state");
                break;
            }
        }
        if(!failed)
            display("search method is correct");
        
        for(int i = 0; i < 500; i++)
            stack.push(new Integer(i));
            
        stack.clear();
        
        // validate size method
        if (stack.size() != 0)
        {
            failure("Failed: incorrect size value");
            return;
        }

        //validate empty method
        if (!stack.empty())
        {
            failure("Failed: incorrect empty method");
            return;
        }
        
        //validate pop throwing StackUnderflowException
        try
        {
            if(stack.pop() != null)
                failure("clear method is incorrect");
        }
        catch (StackUnderflowException e)
        {
        }
        if(!failed)
            display("clear method is correct");
        
        if(!failed)
            System.out.println("Congratulations!  Your " + type + " class is correct\n");

    }
    
    private static void failure(String str)
    {
        if(display)
            System.out.println("*** " + str);
        failed = true;
    }
    
    private static void display(String str)
    {
        if(display)
            System.out.println("Passed " + str);
    }
}