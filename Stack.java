public class Stack<E> {
    private LinkedList<E> stack = new LinkedList<E>();

    public Stack(){
    }

    public void push(E element){
        stack.add(element);
    }

    public E peek(){
        return stack.getLast();
    }

    public E pop(){
        return stack.removeLast();
    }

    public int size(){
        return stack.size();
    }
}
