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
}

class Instructor {
    String name; 
    IList<Course> courses;
    Instructor(String n) {
        this.name = n;
        this.courses = new MtList<Course>();
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
    
    /*public boolean isEnrolled(Course c) {
        return this.courses.courseNames().equals(c.courseName());
    }
    
    // adds the Student to the given course list
    
    void enroll(Course c) {
        //if(this.isEnrolled(c)) {
            throw new RuntimeException("Student is already enrolled!");
        }
        else {
            this.courses = new ConsList<Course>(c, this.courses);
        }
    }
    // determines whether the given Student is in any of the same classes as this Student
    public boolean classmates(Student c) {
        return this.courses.sameCourse(c.sameCourse);
        
    }*/

}

interface IList<T> {
}

class MtList<T> implements IList<T> {
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;
    
    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
}

class ExamplesRegistrar {
    
    Student student1 = new Student("cameron", 1);
    Student student2 = new Student("ethan", 2);
    Student student3 = new Student("muigai", 3);
    Student student4 = new Student("roberta", 4);
    Student student5 = new Student("alex", 5);

}
