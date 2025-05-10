public class Ticket {
    //Ticket attributes
    private int row;
    private int seat;
    private int price;
    private Person person;

    //Constructor to initialize Ticket object
    public Ticket(int row, int seat, int price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //Getter and setter methods for Ticket attributes
    public int getRow(){
        return this.row;
    }
    public void setRow(int row){
        this.row = row;
    }

    public int getSeat(){
        return this.seat;
    }
    public void setSeat(int seat){
        this.seat = seat;
    }

    public int getPrice(){
        return this.price;
    }
    public void setPrice(int price){
        this.price = price;
    }

    public Person getPerson(){
        return this.person;
    }
    public void setPerson(Person person){
        this.person = person;
    }

    //Method to print ticket information
    public void print_ticket(){
        person.print_person(); //Print person information associated with the ticket

        //Print ticket details
        System.out.println(".....Ticket Information.....");
        System.out.println("Row Number: " + row);
        System.out.println("Seat Number: " + seat);
        System.out.println("Ticket Price: £" + price);
    }
}