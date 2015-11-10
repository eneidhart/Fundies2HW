import tester.*; 
interface IComparator<T> {
    // Returns an int:
    //-1 if t1 < t2
    //0 if t1 == t2
    //+1 if t1 > t2
    int compare(T t1, T t2);
}

// compares strings lexicographically (in alphabetical order)
class StringLexCompGen implements IComparator<String> {
    // Returns an int:
    //-1 if t1 < t2
    //0 if t1 == t2
    //+1 if t1 > t2
    public int compare(String t1, String t2) {
        return t1.compareTo(t2);
    }
}

// compares the Strings by their length from the shortest to the longest
class StringLengthCompGen implements IComparator<String> {
    // Returns an int:
    //-1 if t1 < t2
    //0 if t1 == t2
    //+1 if t1 > t2
    public int compare(String t1, String t2) {
        return t1.length() - t2.length();
    }
}

interface IPred<T> {
    //Check if condition applies to given T
    boolean apply(T t);
}

interface IFunc<T, R> {
    //applies an operation to T and produces a R
    R apply(T t);
}



interface IList<T> {
    // confirms whether this list is sorted
    boolean isSorted(IComparator<T> comp);

    // helper method for isSorted
    boolean isSortedHelp(IComparator<T> comp, T t);

    // creates a new list of strings merged with the given sorted list
    IList<T> merge(IList<T> given, IComparator<T> comp);

    // helper method for merge
    IList<T> mergeHelp(IList<T> given);

    // creates a new sorted list, in the order given by the given
    // IStringsCompare function object
    IList<T> sort(IComparator<T> comp);

    // determines if two lists are the same (in data and order)
    boolean sameList(IComparator<T> comp, IList<T> given);

    // Inserts a new element into the correct place on a sorted list
    IList<T> insert(IComparator<T> comp, T t);

    // Helper method for sameListHelp
    public boolean sameFirst(IComparator<T> comp, T t);

    // Helper method for sameList
    public boolean sameListHelp(IComparator<T> comp, IList<T> given);

    // Determines if a list is empty
    public boolean isEmpty();

    // takes this list and a given list and produces the 
    // result of appending the latter onto the former
    public IList<T> append(IList<T> that);

    // filter this list based on the given predicate
    public IList<T> filter(IPred<T> pred);

    // map the list of T to a list of R
    public <R> IList<R> map(IFunc<T, R> func);

    // accepts an IListVisitors
    public <R> R accept(IListVisitor<T, R> visitor);
}


class Empty<T> implements IList<T> {
    // confirms whether this list is sorted
    public boolean isSorted(IComparator<T> comp) {
        return true;
    }

    // helper method for isSorted
    public boolean isSortedHelp(IComparator<T> comp, T t) {
        return true;
    }

    // creates a new list of strings merged with the given sorted list
    public IList<T> merge(IList<T> given, IComparator<T> comp) {
        return given;
    }

    // helper method for merge
    public IList<T> mergeHelp(IList<T> given) {
        return given;
    }

    // creates a new sorted list, in the order given by the given
    // IStringsCompare function object
    public IList<T> sort(IComparator<T> comp) {
        return this;
    }

    // determines if two lists are the same (in data and order)
    public boolean sameList(IComparator<T> comp, IList<T> given) {
        return given.isEmpty();
    }

    // helper method for sameListHelp
    public boolean sameFirst(IComparator<T> comp, T t) {
        return false;
    }

    // helper method for sameList
    public boolean sameListHelp(IComparator<T> comp, IList<T> given) {
        return given.isEmpty();
    }

    // returns true if this list is empty (it is)
    public boolean isEmpty() {
        return true;
    }

    // insert an object into sorted location in this list
    public IList<T> insert(IComparator<T> comp, T t) {
        return new Cons<T>(t, this);
    }

    //add another list to the end of this list
    public IList<T> append(IList<T> that) {
        return that;
    }

    // filter this empty list based on the given predicate
    public IList<T> filter(IPred<T> pred) {
        return this;
    }

    // map the empty list of T to an empty list of R
    public <R> IList<R> map(IFunc<T, R> func) {
        return new Empty<R>();
    }

    // accepts a visitor
    public <R> R accept(IListVisitor<T, R> visitor) {
        return visitor.visit(this);
    }
}

class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    // confirms whether this list is sorted
    public boolean isSorted(IComparator<T> comp) {
        return this.rest.isSortedHelp(comp, this.first);
    }

    // helper method for isSorted
    public boolean isSortedHelp(IComparator<T> comp, T t) {
        return (comp.compare(t, this.first)) <= 0
                && this.rest.isSortedHelp(comp, this.first);
    }

    // creates a new list of strings merged with the given sorted list
    public IList<T> merge(IList<T> given, IComparator<T> comp) {
        return (this.mergeHelp(given)).sort(comp);
    }

    // combines two lists
    public IList<T> mergeHelp(IList<T> given) {
        return new Cons<T>(this.first, given.mergeHelp(this.rest));
    }

    // creates a new sorted list, in the order given by the given
    // IStringsCompare function object
    public IList<T> sort(IComparator<T> comp) {
        return (this.rest.sort(comp)).insert(comp, this.first);
    }

    // determines if two lists are the same (in data and order)
    public boolean sameList(IComparator<T> comp, IList<T> given) {
        return given.sameFirst(comp, this.first)
                && given.sameListHelp(comp, this.rest);
    }

    // helper method for sameListHelp
    public boolean sameFirst(IComparator<T> comp, T t) {
        return this.first.equals(t);
    }

    // Helper method for sameList
    public boolean sameListHelp(IComparator<T> comp, IList<T> given) {
        return given.sameList(comp, this.rest);
    }

    // Determines if a list is empty (it's not)
    public boolean isEmpty() {
        return false;
    }

    // Inserts an element into the correct position of this sorted list
    public IList<T> insert(IComparator<T> comp, T t) {
        if (comp.compare(this.first, t) <= 0) {
            return new Cons<T>(this.first, this.rest.insert(comp, t));
        } 
        else {
            return new Cons<T>(t, this);
        }
    }

    // appends another list to the end of this one
    public IList<T> append(IList<T> that) {
        return new Cons<T>(this.first, this.rest.append(that));
    } 

    // filter this list based on the given predicate
    public IList<T> filter(IPred<T> pred) {
        if (pred.apply(this.first)) {
            return new Cons<T>(this.first, this.rest.filter(pred));
        } 
        else {
            return this.rest.filter(pred);
        }
    }
    
    // map the list of T to a list of R
    public <R> IList<R> map(IFunc<T, R> func) {
        return new Cons<R>(func.apply(this.first), this.rest.map(func));
    }

    //accepts a visitor
    public <R> R accept(IListVisitor<T, R> visitor) {
        return visitor.visit(this);
    }
}

interface IListVisitor<T, R> {
    R visit(Empty<T> that);
    R visit(Cons<T> that);
}

// a visitor which applies a function to every item in a list
class MapVisitor<T, R> implements IListVisitor<T, IList<R>> {
    IFunc<T, R> func;

    MapVisitor(IFunc<T, R> func) {
        this.func = func;
    }

    //visit an empty list
    public IList<R> visit(Empty<T> that) {
        return new Empty<R>();
    }

    //visit a cons list, applying the function to every item therein
    public IList<R> visit(Cons<T> that) {
        return new Cons<R>(func.apply(that.first), that.rest.accept(this));
    }
}

class ExamplesLists {

    
}
