abstract class ANode<T> {
    ANode<T> next;
    ANode<T> prev;
    
    ANode(ANode<T> p, ANode<T> n) {
        this.prev = p;
        this.next = n;
    }
    
    public void setPrev(ANode<T> p) {
        this.prev = p;
    }
    
    public void setNext(ANode<T> n) {
        this.next = n;
    }
    
    public ANode<T> getNext() {
        return this.next;
    }
    
    public ANode<T> getPrev() {
        return this.prev;
    }
    
    abstract boolean isSentinel();
    
    abstract T getData();
}

class Node<T> extends ANode<T> {
    T data;
    
    Node(T data) {
        super(null, null);
        this.data = data;
    }
    
    Node(T data, ANode<T> p, ANode<T> n) {
        super(p, n);
        
        if (p == null || n == null) {
            throw new IllegalArgumentException("Given null node.");
        }
        
        this.data = data;
        this.prev.setNext(this);
        this.next.setPrev(this);
    }
    
    boolean isSentinel() {
        return false;
    }
    
    T getData() {
        return this.data;
    }
}

class Sentinel<T> extends ANode<T> {
    Sentinel() {
        super(null, null);
        this.next = this;
        this.prev = this;
    }
    
    boolean isSentinel() {
        return true;
    }
    
    T getData() {
        throw new RuntimeException("Cannot get data of a sentinel.");
    }
}

class Deque<T> {
    Sentinel<T> header;
    
    Deque() {
        this.header = new Sentinel<T>();
    }
    
    Deque(Sentinel<T> h) {
        this.header = h;
    }
    
    int size() {
        ANode<T> curr = this.header;
        int size = 0;
        while (curr.getNext() != null && !curr.next.isSentinel()) {
            curr = curr.getNext();
            size++;
        }
        return size;
    }
    
    void addAtHead(Node<T> add) {
        add.setNext(this.header.getNext());
        add.setPrev(this.header);
        this.header.setNext(add);
    }
    
    void addAtTail(Node<T> add) {
        add.setPrev(this.header.getPrev());
        add.setNext(this.header);
        this.header.setPrev(add);
    }
    
    void removeFromHead() {
        if (header.getNext() == null) {
            throw new RuntimeException("Cannot remove from empty list.");
        }
        else {
            header.setNext(header.next.getNext());
        }
    }
    
    void removeFromTail() {
        if (header.getPrev() == null) {
            throw new RuntimeException("Cannot remove from empty list.");
        }
        else {
            header.setPrev(header.prev.getPrev());
        }
    }
    
    ANode<T> find(IPred<T> pred) {
        ANode<T> curr = this.header.getNext();
        boolean found = false;
        while (!curr.isSentinel()) {
            if (!found) {
                found = pred.apply(curr.getData());
                if (!found) {
                    curr = curr.getNext();
                }
            }
        }
        if (!found) {
            curr = this.header;
        }
        return curr;
    }
    
    void removeNode(ANode<T> given) {
        if (given.isSentinel()) {
            return;
        }
        IPred<T> same = new SameAs<T>(given.getData());
        ANode<T> found = this.find(same);
        if (!found.isSentinel()) {
            found.prev.setNext(found.getNext());
            found.next.setPrev(found.getPrev());
        }
    }
}

interface IPred<T> {
    public boolean apply(T t);
}

class StartsWithA implements IPred<String> {
    public boolean apply(String t) {
        return t.toLowerCase().startsWith("a");
    }
}

class SameAs<T> implements IPred<T> {
    T data;
    
    SameAs(T d) {
        this.data = d;
    }
    
    public boolean apply(T t) {
        return this.data.equals(t);
    }
}

class ExamplesDeque {
    Deque<String> deque1;
    Deque<String> deque2;
    Deque<String> deque3;
    
    void init() {
        deque1 = new Deque<String>();
        Sentinel<String> d2 = new Sentinel<String>();
        Node<String> abc;
        Node<String> bcd = new Node<String>("bcd");
        Node<String> cde = new Node<String>("cde");
        Node<String> def = new Node<String>("def");
        abc = new Node<String>("abc", d2, new Node<String>("bcd", abc, cde));
        deque2 = new Deque<String>();
    }
}