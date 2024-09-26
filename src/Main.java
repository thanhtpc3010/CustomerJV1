import Entity.Customer;
import Entity.Invoice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        // Test Customer class
        Customer c1 = new Customer(1, "Adam", 10);
        Customer c2 = new Customer(2, "Johnson", 15);
        Customer c3 = new Customer(3, "Kyle", 5);

        // Create a list of Invoices
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(101, c1, 888.8));
        invoices.add(new Invoice(102, c2, 1000));
        invoices.add(new Invoice(103, c3, 500));
        invoices.add(new Invoice(104, c1, 1200));
        invoices.add(new Invoice(105, c2, 1500));
        invoices.add(new Invoice(106, c3, 300));

        // 1. Display info Invoice by ID of Customer (using Stream API)
        int customerIDToSearch = 1;
        System.out.println("Invoices for customer ID " + customerIDToSearch + ":");
        invoices.stream()
                .filter(invoice -> invoice.getCustomerID() == customerIDToSearch)
                .forEach(System.out::println);

        // 2. Get Info of Customer has total getAmount() is MAX
        Customer maxCustomer = Stream.of(c1, c2, c3)
                .max((cust1, cust2) -> Double.compare(
                        invoices.stream()
                                .filter(inv -> inv.getCustomerID() == cust1.getID())
                                .mapToDouble(Invoice::getAmount)
                                .sum(),
                        invoices.stream()
                                .filter(inv -> inv.getCustomerID() == cust2.getID())
                                .mapToDouble(Invoice::getAmount)
                                .sum()
                ))
                .orElse(null);

        System.out.println("Customer with max total amount: " + maxCustomer);

        // 3. Get info Customer has setDiscount is MIN
        Customer minDiscountCustomer = Stream.of(c1, c2, c3)
                .min(Comparator.comparingInt(Customer::getDiscount))
                .orElse(null);

        System.out.println("Customer with minimum discount: " + minDiscountCustomer);

        // 4. Search and display Customer via Invoice(Id) or Customer(Name)
        // Search by Invoice ID
        int searchInvoiceID = 102;
        System.out.println("Search by Invoice ID:");
        invoices.stream()
                .filter(invoice -> invoice.getID() == searchInvoiceID)
                .map(Invoice::getCustomer)  // Lấy ra Customer từ Invoice
                .forEach(System.out::println);

        // Search by Customer Name
        String searchName = "Kyle";
        System.out.println("Search by Customer Name:");
        invoices.stream()
                .filter(invoice -> invoice.getCustomerName().equalsIgnoreCase(searchName))
                .map(Invoice::getCustomer)
                .forEach(System.out::println);
    }}