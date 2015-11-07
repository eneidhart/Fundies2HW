import tester.Tester;
// import tester.*;


// represents a Person with a user name and a list of buddies
class Person {

    String username;
    ILoBuddy buddies;

    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
    }

    // returns true if this Person has that as a direct buddy
    public boolean hasDirectBuddy(Person that) {
        // We call those on the person’s buddy list the direct buddies
        return this.buddies.contains(that.username); 
    }

    // returns the number of people that are direct buddies 
    // of both this and that person
    int countCommonBuddies(Person that) {
        return this.buddies.countInCommon(that.buddies);
    }

    // returns the number of people who will show up at the party 
    // given by this person
    int partyCount() {
        return this.partyCountHelper(new MTLoBuddy());
    }
    
    // to determine how many people are coming to this party 
    int partyCountHelper(ILoBuddy buds) {
        return  this.buddies.countDirect(new ConsLoBuddy(this, buds)) + 
                this.buddies.partyCount(new ConsLoBuddy(this, buds));
    }

    // will the given person be invited to a party 
    // organized by this person?
    boolean hasDistantBuddy(Person that) {
        return this.buddies.checkBuddies(that, this.buddies);
    }
    // TODO
    boolean hasDistantBuddyHelper(Person that, ILoBuddy checked) {
        if (!checked.inList(this) && this.buddies.inList(that)) {
            return true;
        }
        else {
            return this.buddies.checkBuddies(that, new ConsLoBuddy(this, checked));
        }
    }
    
    // returns the name of a person
    public String getName() {
        return this.username;
    }
    // returns the list of buddies for a person
    public ILoBuddy getBuddies() {
        return this.buddies;
    }

    // determines if this person has the same name as that person
    public boolean samePerson(Person buddy) {
        return this.username.equals(buddy.username);
    }


    // Change this person's buddy list so that it includes the given person
    void addBuddy(Person buddy) {
        if (!buddy.samePerson(this)) {      
            this.buddies = new ConsLoBuddy(buddy, this.buddies);
        }
        else {
            throw new RuntimeException("This person is already in your buddies list!");
        }
    }
}

//represents a list of Person's buddies
interface ILoBuddy {
    // to determine if the given person is in this list
    boolean inList(Person that);
    // determines if a list contains a person given a username
    boolean contains(String name);
    // to count the amount of buddies that appear in this list and the given list
    int countInCommon(ILoBuddy buddies);
    // TODO
    boolean checkBuddies(Person that, ILoBuddy checked); 
    // computes the number of items in a list
    int countDirect(ILoBuddy buddies);
    // to count the number of people in this party
    int partyCount(ILoBuddy buddies);

}
//represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
    MTLoBuddy() {/*empty constructor*/}
    //to determine if the given person is in this list
    public boolean inList(Person that) {
        return false;
    }
    // returns true if this empty list contains a person with the given name
    public boolean contains(String name) {
        return false;
    }
    // to count the amount of buddies that appear in this 
    // list and the given list
    public int countInCommon(ILoBuddy buddies) {
        return 0;
    }
    // will the given person be invited to a party organized by this person?
    public boolean checkBuddies(Person that, ILoBuddy checked) {
        return false;
    }
    // computes the number of items in an empty list
    public int countDirect(ILoBuddy buddies) {
        return 0;
    }
    // to count the number of people in this party
    public int partyCount(ILoBuddy buddies) {
        return 0;
    }
}

//represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

    Person first;
    ILoBuddy rest;

    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }
 
    //to determine if the given person is in this list
    public boolean inList(Person that) {
        if (this.first.username.equals(that.username)) {
            return true;
        }
        else {
            return this.rest.inList(that);
        }
    }

    // Returns true if this non-empty list contains a person with the given name
    public boolean contains(String name) {
        return this.first.getName().equals(name) || 
                this.rest.contains(name);
    }

    // computes how many times a name on a list of buddies occurs
    public int countInCommon(ILoBuddy that) {
        if (that.contains(this.first.getName())) {
            return 1 + this.rest.countInCommon(that);
        }
        else {
            return this.rest.countInCommon(that);
        }
    }

    // TODO
    public boolean checkBuddies(Person that, ILoBuddy checked) {
        if (checked.inList(this.first) &&
                this.first.hasDistantBuddyHelper(that, checked)) {
            return true;
        }
        else {
            return this.rest.checkBuddies(that, new ConsLoBuddy(this.first, checked));
        }
    }
    // computes the number of items in a list
    public int countDirect(ILoBuddy checked) {
        if (!checked.inList(this.first)) {
            return 1 + this.rest.countDirect(new ConsLoBuddy(this.first, checked));
        }
        else {
            return this.rest.countDirect(checked);
        }
    }
    // to count the number of people in this party
    public int partyCount(ILoBuddy checked) {
        if (!checked.inList(this.first)) {
            return this.first.partyCountHelper(checked) 
                    + this.rest.partyCount(new ConsLoBuddy(this.first, checked)); 
        }
        else {
            return this.rest.partyCount(checked);
        }
    }
}

//runs tests for the buddies problem
class ExamplesBuddies {
    Person ann;
    Person bob;
    Person cole;
    Person dan;
    Person ed;
    Person fay;
    Person gabi;
    Person hank;
    Person jan;
    Person kim;
    Person len;
    void initBuddies() {
        // initial buddy set of data
        this.ann = new Person("ann");
        this.bob = new Person("bob");
        this.cole = new Person("cole");
        this.dan = new Person("dan");
        this.ed = new Person("ed");
        this.fay = new Person("fay");
        this.gabi = new Person("gabi");
        this.hank = new Person("hank");
        this.jan = new Person("jan");
        this.kim = new Person("kim");
        this.len = new Person("len");

        ann.addBuddy(bob);
        ann.addBuddy(cole);
        bob.addBuddy(ann);
        bob.addBuddy(ed);
        bob.addBuddy(hank);
        cole.addBuddy(dan);
        dan.addBuddy(cole);
        ed.addBuddy(fay);
        fay.addBuddy(ed);
        fay.addBuddy(gabi);
        gabi.addBuddy(ed);
        gabi.addBuddy(fay);

        jan.addBuddy(kim);
        jan.addBuddy(len);
        kim.addBuddy(jan);
        kim.addBuddy(len);
        len.addBuddy(jan);
        len.addBuddy(kim);
    }
    // TODO
    boolean testInList(Tester t) {
        this.initBuddies();
        return t.checkExpect(this.ann.buddies.inList(this.bob), true) &&
                t.checkExpect(this.hank.buddies.inList(this.ann), false);
    }

    // tests the HasDirectBuddy method
    boolean testHasDirectBuddy(Tester t) {
        // init data resets the data
        initBuddies();
        return t.checkExpect(this.ann.getBuddies().contains("bob"), true) &&
                t.checkExpect(this.bob.getBuddies().contains("cole"), false) &&
                t.checkExpect(this.hank.getBuddies().contains("fay"), false);
    }

    // tests the Count Common Buddies method
    boolean testCountCommonBuddies(Tester t) {
        initBuddies();
        return t.checkExpect(this.ann.countCommonBuddies(this.bob), 0) &&
                t.checkExpect(this.bob.countCommonBuddies(this.fay), 1) &&
                t.checkExpect(this.jan.countCommonBuddies(this.kim), 1) &
                t.checkExpect(this.len.countCommonBuddies(this.cole), 0);
    }

    // tests the samePerson method
    boolean testSamePerson(Tester t) {
        initBuddies();
        return t.checkExpect(this.ann.samePerson(this.ann), true) &&
                t.checkExpect(this.bob.samePerson(this.bob), true) &&
                t.checkExpect(this.jan.samePerson(this.fay), false) &&
                t.checkExpect(this.kim.samePerson(this.gabi), false);
    }
    // TODO
    boolean testCountDirect(Tester t) {
        initBuddies();
        return t.checkExpect(this.hank.buddies.countDirect(new MTLoBuddy()), 0) &&
                t.checkExpect(this.dan.buddies.countDirect(new MTLoBuddy()), 1) &&
                t.checkExpect(this.ann.buddies.countDirect(new MTLoBuddy()), 2) &&
                t.checkExpect(this.bob.buddies.countDirect(new MTLoBuddy()), 3);
    }
    // TODO
    boolean testGetName(Tester t) {
        initBuddies();
        return t.checkExpect(this.ann.getName(), "ann") &&
                t.checkExpect(this.bob.getName(), "bob") &&
                t.checkExpect(this.cole.getName(), "cole") &&
                t.checkExpect(this.fay.getName(), "fay");
    }
    // tests the getBuddies method
    boolean testGetBuddies(Tester t) {
        initBuddies();
        return t.checkExpect(this.ann.getBuddies(), new ConsLoBuddy(this.cole, 
                new ConsLoBuddy(this.bob, new MTLoBuddy()))) &&
                t.checkExpect(this.hank.getBuddies(), new MTLoBuddy()) &&
                t.checkExpect(this.jan.getBuddies(), new ConsLoBuddy(this.len, 
                        new ConsLoBuddy(this.kim, new MTLoBuddy())));
    }
    // test the hasDistantBuddy() method
    boolean testHasDistantBuddy(Tester t) {
        initBuddies();
        return t.checkExpect(this.ann.hasDistantBuddy(kim), false) &&
                t.checkExpect(this.kim.hasDistantBuddy(len), true) &&
                t.checkExpect(this.ann.hasDistantBuddy(gabi), true) &&
                t.checkExpect(this.hank.hasDistantBuddy(this.fay), false) &&
                t.checkExpect(this.bob.hasDistantBuddy(this.cole), true) &&
                t.checkExpect(this.ed.hasDistantBuddy(this.gabi), true);

    }
    // test the partyCount() method
    boolean testPartyCount(Tester t) {
        this.initBuddies();
        return t.checkExpect(this.ann.partyCount(), 8) &&
                t.checkExpect(this.jan.partyCount(), 3) &&
                t.checkExpect(this.fay.partyCount(), 3) &&
                t.checkExpect(this.dan.partyCount(), 1);
    }
}
