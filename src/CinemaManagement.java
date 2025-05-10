import java.util.InputMismatchException;
import java.util.Scanner;

public class CinemaManagement {
    public static void main(String[] args) {
        int[][] cinema_seats = new int[3][16]; //Initialize the array to store cinema seats

        //Initialize all the seats are available
        for (int row_num = 0; row_num < cinema_seats.length; row_num++) {
            for (int colum_num = 0; colum_num < cinema_seats[row_num].length; colum_num++) {
                cinema_seats[row_num][colum_num] = 0;
            }
        }

        Ticket[] tickets = new Ticket[48]; //Initialize array to store tickets

        select_option(cinema_seats, tickets); //Call the select option method
    }

    public static void select_option(int[][] cinema_seats, Ticket[] tickets){
        int option;
        Scanner input = new Scanner(System.in);

        try{
            do{
                //Display menu options
                System.out.println("\n          WELCOME TO THE LONDON LUMIERE");
                System.out.println("\n------------------------------------------------");
                System.out.println("Please select an option:");
                System.out.println("   1) Buy a ticket");
                System.out.println("   2) Cancel ticket");
                System.out.println("   3) See seating plan");
                System.out.println("   4) Find first seat available");
                System.out.println("   5) Print tickets information and total price");
                System.out.println("   6) Search ticket");
                System.out.println("   7) Sort tickets by price");
                System.out.println("   8) Exit");
                System.out.println("------------------------------------------------");
                System.out.println("\nSelect option:");

                option = input.nextInt();

                // Check validation of the user input
                if(option < 1 || option > 8){
                    System.out.println("Please select a correct option.");
                    continue;
                }

                //Switch statement to perform actions based on user input
                switch (option){
                    case 1:
                        buy_ticket(cinema_seats, tickets);
                        break;
                    case 2:
                        cancel_ticket(cinema_seats, tickets);
                        break;
                    case 3:
                        print_seating_area(cinema_seats);
                        break;
                    case 4:
                        find_first_available(cinema_seats);
                        break;
                    case 5:
                        print_tickets_info(tickets);
                        break;
                    case 6:
                        search_ticket(cinema_seats, tickets);
                        break;
                    case 7:
                        sort_tickets(tickets);
                        break;
                    case 8:
                        System.out.println("Exiting the London Lumiere.");
                        break;
                }
            }while (option != 8); //Continue loop until user selects to quit
            //Handle invalid input type
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter an integer.");
            select_option(cinema_seats, tickets);
        }

    }

    //Method to buy a ticket
    private static void buy_ticket(int[][] cinema_seats, Ticket[] tickets) {
        System.out.println("\n*****Book Your Ticket*****");

        Scanner s = new Scanner(System.in);
        //Asks the user to enter row number
        System.out.println("Please enter a row number(1-3) of the seat:");
        while (true) {
            try {
                int row_index = s.nextInt();
                int row_number = row_index - 1;
                //Check validation of the row number
                if (row_index < 1 || row_index > 3) {
                    System.out.println("Invalid row number. Please enter a row number(1-3) of the seat:");
                    continue;
                }

                //Asks the user to enter seat number
                System.out.println("Please enter a seat number(1-16) of the seat:");
                while (true) {
                    try {
                        int seat_index = s.nextInt();
                        int seat_num = seat_index - 1;
                        //Check validation of the seat number
                        if (seat_index < 1 || seat_index > 16) {
                            System.out.println("Invalid seat number. Please enter a seat number(1-16) of the seat:");
                            continue;
                        }

                        System.out.println("Seat " + seat_index + " in row " + row_index);

                        //Check seat availability
                        if (cinema_seats[row_number][seat_num] == 1) {
                            System.out.println("This seat is not available. Please book another seat.");
                            buy_ticket(cinema_seats, tickets);
                            return;
                        } else {
                            //Asks the user to enter personal details and check validation of the personal details
                            System.out.println("\nPlease enter your name:");
                            while (true) {
                                String name = s.next().trim();
                                if (!name.matches("[a-zA-Z]+")) {
                                    System.out.println("Invalid name format. Please enter your name:");
                                    continue;
                                }
                                System.out.println("Please enter your surname:");
                                while (true) {
                                    String surname = s.next().trim();
                                    if (!surname.matches("[a-zA-Z]+")) {
                                        System.out.println("Invalid surname format. Please enter your surname:");
                                        continue;
                                    }
                                    System.out.println("Please enter your email:");
                                    while (true) {
                                        String email = s.next();
                                        if (!email.contains("@") || !email.contains(".")) {
                                            System.out.println("Invalid email format. Please enter a valid email address.");
                                            continue;
                                        }

                                        Person person = new Person(name, surname, email);

                                        //Determine ticket price based on row
                                        int price;
                                        if (row_index == 1) {
                                            price = 12;
                                        } else if (row_index == 2) {
                                            price = 10;
                                        } else {
                                            price = 8;
                                        }
                                        Ticket ticket = new Ticket(row_index, seat_index, price, person);

                                        if (cinema_seats[row_number][seat_num] == 0) {
                                            System.out.println("The seat " + seat_index + " in row " + row_index + " has been booked.");
                                            cinema_seats[row_number][seat_num] = 1;

                                            //Store the ticket to the corresponding seat in the tickets array
                                            switch (row_index) {
                                                case 1 -> tickets[seat_num] = ticket;
                                                case 2 -> tickets[seat_num + 16] = ticket;
                                                case 3 -> tickets[seat_num + 32] = ticket;
                                            }
                                        }
                                        return; //Exit the method after successful booking
                                    }
                                }
                            }
                        }
                        //Handle invalid inputs
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid seat number(1-16) of the seat:");
                        s.next(); //Clear invalid input
                    }
                }
                //Handle invalid inputs
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid row number(1-3) of the seat:.");
                s.next(); //Clear invalid input
            }
        }
    }

    //Method to a cancel ticket
    private static void cancel_ticket(int[][] cinema_seats, Ticket[] tickets){
        System.out.println("\n*****Cancel Your Ticket*****");

        Scanner s = new Scanner(System.in);
        //Asks the user to enter row number
        System.out.println("Please enter a row number(1-3) of the seat:");
        while (true) {
            try {
                int row_index = s.nextInt();
                int row_number = row_index - 1;
                //Check validation of row number
                if (row_index < 1 || row_index > 3) {
                    System.out.println("Invalid row number. Please enter a row number(1-3) of the seat:");
                    continue;
                }

                //Asks the user to enter seat number
                System.out.println("Please enter a seat number(1-16) of the seat:");
                while (true) {
                    try {
                        int seat_index = s.nextInt();
                        int seat_num = seat_index - 1;
                        //Check validation of seat number
                        if (seat_index < 1 || seat_index > 16) {
                            System.out.println("Invalid seat number. Please enter a seat number(1-16) of the seat:");
                            continue;
                        }

                        //Check the seat is canceled or not
                        if (cinema_seats[row_number][seat_num] == 0) {
                            System.out.println("The seat " + seat_index + " in row " + row_index + " is already available. Please select another seat.");
                        } else if (cinema_seats[row_number][seat_num] == 1) {
                            cinema_seats[row_number][seat_num] = 0;
                            System.out.println("The seat " + seat_index + " in row " + row_index + " has been cancelled.");

                            //Remove the corresponding ticket from the tickets array
                            switch (row_index) {
                                case 1 -> tickets[seat_num] = null;
                                case 2 -> tickets[seat_num + 16] = null;
                                case 3 -> tickets[seat_num + 32] = null;
                            }
                        }
                        return;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid seat number(1-16) of the seat:");
                        s.next();
                    }
                }
                //Handle invalid inputs
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid row number(1-3) of the seat:");
                s.next();
            }
        }
    }

    //Method to see seating plan
    private static void print_seating_area(int[][] cinema_seats){
        //Display the seating plan
        System.out.println("\n*****************");
        System.out.println("*     SCREEN    *");
        System.out.println("*****************");

        for (int row_num = 0; row_num < cinema_seats.length; row_num++){
            for (int colum_num = 0; colum_num < cinema_seats[row_num].length; colum_num++){
                if(cinema_seats[row_num][colum_num] == 1){
                    System.out.print("X\t");
                }else {
                    System.out.print("0\t");
                }
                if(colum_num == 7){
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }

    //Method to find first seat available
    private static void find_first_available(int[][] cinema_seats){
        System.out.println("\n*****Find First Seat Available*****");
        //Find and print the first available cinema seat
        for(int i = 0; i < cinema_seats.length; i++){
            for (int j = 0; j < cinema_seats[i].length; j++){
                if(cinema_seats[i][j] == 0){
                    System.out.println("The first seat available is seat " + (j + 1) + " in row " + (i+1) + ".");
                    return;
                }
            }
        }
        System.out.println("Sorry, no available seats.");
    }

    //Method to print tickets information and total price
    private static void print_tickets_info(Ticket[] tickets){
        System.out.println("\n*****Cinema Ticket Information*****");

        //Calculate total sales of the tickets
        int total = 0;
        for (Ticket ticket : tickets){
            if(ticket != null){ //Check if the current ticket is not null
                ticket.print_ticket(); //Display the information of the ticket
                total += ticket.getPrice(); //Add the ticket price to the total price
            }
        }
        System.out.println("\nTotal ticket price: £" + total);
    }

    //Method to search ticket
    private static void search_ticket(int[][] cinema_seats, Ticket[] tickets) {
        System.out.println("\n*****Search Ticket*****");

        Scanner s = new Scanner(System.in);
        //Asks the user to enter row number
        System.out.println("Please enter a row number(1-3) of the seat:");
        while (true) {
            try {
                int row_index = s.nextInt();
                int row_number = row_index - 1;
                //Check validation of row number
                if (row_index < 1 || row_index > 3) {
                    System.out.println("Invalid row number. Please enter a row number(1-3) of the seat:");
                    continue;
                }

                //Asks the user to enter seat number
                System.out.println("Please enter a seat number(1-16) of the seat:");
                while (true) {
                    try {
                        int seat_index = s.nextInt();
                        int seat_num = seat_index - 1;
                        //Check validation of seat number
                        if (seat_index < 1 || seat_index > 16) {
                            System.out.println("Invalid seat number. Please enter a seat number(1-16) of the seat:");
                            continue;
                        }

                        //Check the seat is available both in the cinema seats array and in the tickets array
                        if (cinema_seats[row_number][seat_num] == 0 && tickets[seat_num] == null) {
                            System.out.println("This seat is available.");
                            return;
                        } else {
                            for (Ticket ticket : tickets) {
                                if (ticket != null) {
                                    if (ticket.getRow() == row_index && ticket.getSeat() == seat_index) {
                                        ticket.print_ticket(); //Display the ticket information
                                        return;
                                    }
                                }
                            }
                            System.out.println("No ticket found for the specified seat.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid seat number(1-16) of the seat:");
                        s.next(); //Clear invalid input
                    }
                }
                //Handle invalid input
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid row number(1-3) of the seat:");
                s.next(); //Clear invalid input
            }
        }
    }

    //Method to sort tickets by price
    private static void sort_tickets(Ticket[] tickets){
        System.out.println("\n*****Sort Tickets By Price*****");
        //Sort the tickets by price using Bubble Sort
        for (int i = 0; i < tickets.length - 1; i++){
            for (int j = 0; j < tickets.length - 1 - i; j++){
                if (tickets[j] == null || tickets[j + 1] == null) { //Check if the ticket is null
                    //When the ticket is null, move it to the last place
                    if (tickets[j] == null) {
                        Ticket temp = tickets[j];
                        tickets[j] = tickets[j + 1];
                        tickets[j + 1] = temp;
                    }
                    else {
                        Ticket temp = tickets[j + 1];
                        tickets[j + 1] = tickets[j];
                        tickets[j] = temp;
                    }
                }
                //Comparing their ticket prices
                else if (tickets[j].getPrice() > tickets[j + 1].getPrice()) {
                    //Swap the ticket with the temp ticket made
                    Ticket temp = tickets[j];
                    tickets[j] = tickets[j + 1];
                    tickets[j + 1] = temp;
                }
            }
        }
        //Print sorted tickets
        for (Ticket ticket : tickets){
            if(ticket != null){
                ticket.print_ticket();
            }
        }
    }
}