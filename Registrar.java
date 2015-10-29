import tester.*;  
class Course {
    String name;
    Instructor instructor;
    IList<Student> students;
    
    Course(String n, Instructor i) {
        this.name = n;
        this.instructor = i;
        this.instructor.addCourse(this);
        this.students = new MtList<Student>();
    }
    
    public void addStudent(Student that) {
        this.students.append(new ConsList<Student>(that, new MtList<Student>()));
    }
    
    public String getName() {
        return this.name;
    }
    
    public Instructor getInstructor() {
        return this.instructor;
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
    
    public void addCourse(Course c) {
        this.courses.append(new ConsList<Course>(c, new MtList<Course>()));
    }
    
    public boolean dejavu(Student that) {
        return this.courses.containsElements(that.getCourses()) > 1;
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
    
    public IList<Course> getCourses() {
        return this.courses;
    }
    
    public void enroll(Course c) {
        if(this.courses.contains(c)) {
            throw new RuntimeException("Student is already enrolled!");
        }
        else {
            this.courses = new ConsList<Course>(c, this.courses);
            c.addStudent(this);
        }
    }
    // determines whether the given Student is in any of the same classes as this Student
    public boolean classmates(Student that) {
        return this.courses.containsElements(that.getCourses()) > 0;

    }

}

interface IList<T> {
    public boolean contains(T that);
    public IList<T> append(IList<T> that);
    public int containsElements(IList<T> that);
}

class MtList<T> implements IList<T> {
    public boolean contains(T that) {
        return false;
    }
    
    public IList<T> append(IList<T> that) {
        return that;
    }
    
    public int containsElements(IList<T> that) {
        return 0;
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
    
    public IList<T> append(IList<T> that) {
        return new ConsList<T>(this.first, this.rest.append(that));
    } 
    
    public int containsElements(IList<T> that) {
        if (that.contains(this.first)) {
            return 1 + that.containsElements(this.rest);
        }
        else {
            return that.containsElements(this.rest);
        }
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
    
    Course fundies1 = new Course("Fundamentals 1", this.instructor3);
    Course fundies2 = new Course("Fundamentals 2", this.instructor1);
    Course ood = new Course("Object Oriented Design", this.instructor1);
    Course logic = new Course("Logic and Computation", this.instructor3);
}
