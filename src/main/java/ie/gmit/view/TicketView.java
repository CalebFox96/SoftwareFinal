package ie.gmit.view;

import ie.gmit.contract.TicketContract;
import ie.gmit.data.Country;
import ie.gmit.data.Order;
import ie.gmit.data.Ticket;
import ie.gmit.view.component.InputManager;

import java.io.PrintStream;
import java.util.List;

public class TicketView implements TicketContract.View {

    private final InputManager inputManager;
    private final PrintStream outputStream;
    private final TicketContract.Presenter presenter;

    public TicketView(
            InputManager inputManager, PrintStream printStream, TicketContract.Presenter presenter
    ) {
        this.inputManager = inputManager;
        this.outputStream = printStream;
        this.presenter = presenter;
    }

    @Override
    public void start() {
        presenter.attach(this);
    }

    @Override
    public void countryMenu(List<Country> countries) {
        outputStream.println("Here is a list of the countries we have tickets for:");
        int num = 1;
        for (Country country : countries) {
            outputStream.println(num + ". " + country.getName());
            num++;
        }
        outputStream.println("\n0. Quit");
        int selectNumber = inputManager.getInputInt("Please pick a country: ");

        Country selected = null;
        if (selectNumber > 0) {
            if (selectNumber <= countries.size()) {
                selected = countries.get(selectNumber - 1);
            }

            presenter.chooseCountry(selected);
        }
        //exit
    }

    @Override
    public void ticketMenu(Country country, List<Ticket> tickets) {
        outputStream.println("You have chosen " + country.getName() + " teams");
        outputStream.println("The following teams have tickets available for matches. Please pick\n");
        int num = 1;
        for (Ticket ticket : tickets) {
            outputStream.println(num + ". " + ticket.getTicketCode());
            num++;
        }
        int selectIndex = inputManager.getInputInt("Please pick a ticket: ");

        Ticket selected = null;
        if (selectIndex < tickets.size()) {
            selected = tickets.get(selectIndex);
        }

        presenter.chooseTicket(selected);
    }

    @Override
    public void quantityMenu(Ticket ticket) {
        printTicket(ticket);
        int quantity = inputManager.getInputInt("\nTicket quantity: ");

        Order order = new Order(ticket, quantity);

        presenter.placeOrder(order);
    }

    @Override
    public void orderMenu(List<Order> orders, Order order) {
        printTicket(order.getTicket());

        int total = 0;

        for(Order owned : orders) {
            total += owned.getQuantity();
        }
        outputStream.println();
        outputStream.println("Product owned: " + total);
        outputStream.println("New Product: " + order.getQuantity());

        String confirm = inputManager.getInputString("Continue? (y/n):");

        if (confirm.length() > 0 && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {
            presenter.confirmOrder(order);
        } else {
            presenter.confirmOrder(null);
        }
    }

    private void printTicket(Ticket ticket) {
        outputStream.println("Match:      " + ticket.toString());
        outputStream.println("Location:   " + ticket.getLocation());
        outputStream.println("Price:      " + ticket.getPriceFormatted());
        outputStream.println("Date:       " + ticket.getDate());
        outputStream.println("Seat No:    " + ticket.getSeatNumber());
        outputStream.println("Ticket No:  " + ticket.getTicketNumber());
    }
}
