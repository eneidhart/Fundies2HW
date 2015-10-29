import tester.*;  
class Course {
    String name;
    Instructor instructor;
    IList<Student> students;
    Course(String n, Instructor i, IList<Student> s) {
        this.name = n;
        this.instructor = i;
        this.students = s;
    }
    public String getName() {
        return this.name;
    }
    public boolean sameCourse(Course that) {
        return this.name.equals(that.name) &&
                this.instructor.sameInstructor(that.instructor);
    }
    
    
}

class Instructor {
    String name; 
    IList<Course> courses;
    Instructor(String n) {
        this.name = n;
        this.courses = new MtList<Course>();
    }
    public String getName() {
        return this.name;
    }
    public boolean sameInstructor(Instructor that) {
        return this.name.equals(that.name);
    }
}

class Student {
    String name;
    int id;
    IList<Course> courses;
    Student(String n, int id) {
        this.name = n;
        this.id = id;
        this.courses = new MtList<Course>();
    }
    public String getName() {
        return this.name;
    }
    public int getId() {
        return this.id;
    }
    public boolean sameStudent(Student that) {
        return this.name.equals(that.name) &&
                this.id == that.id;
    }
    void enroll(Course c) {
        if(this.courses.contains(this)) {
            throw new RuntimeException("Student is already enrolled!");
        }
        else {
            this.courses = new ConsList<Course>(c, this.courses);
        }
    }
    // determines whether the given Student is in any of the same classes as this Student
    public boolean classmates(Student c) {
        return this.student.contains(c);

    }

}

interface IList<T> {
    boolean contains(T that);

}

class MtList<T> implements IList<T> {
    public boolean contains(T that) {
        return false;
    }
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;

    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public boolean contains(T that) {
        return this.first.equals(that) ||
                this.rest.contains(that);
    }
    public <R> R accept(IListVisitor<T, R> fun) {
        return fun.visitMtList<T>(this);
    }
}

interface IListVisitor<T, R> {
    R visit(MtList<T> that);
    R visit(ConsList<T> that);
}

class MapVisitor<T, R> implements IListVisitor<T, IList<R>> {
    IFunc<T, R> func;

    MapVisitor(IFunc<T, R> func) {
        this.func = func;
    }

    public IList<R> visit(Empty<T> that) {
        return new Empty<R>();
    }

    public IList<R> visit(Cons<T> that) {
        return new Cons<R>(func.apply(that.first), that.rest.accept(this));
    }
}

class ExamplesRegistrar {

    Student student1 = new Student("cameron", 1);
    Student student2 = new Student("ethan", 2);
    Student student3 = new Student("muigai", 3);
    Student student4 = new Student("roberta", 4);
    Student student5 = new Student("alex", 5);
    
    Instructor instructor1 = new Instructor("nada");
    Instructor instructor2 = new Instructor("nat");
    Instructor instructor3 = new Instructor("leena");
    Instructor instructor4 = new Instructor("olin");
    Instructor instructor5 = new Instructor("ben");
}
