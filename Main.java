import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// import javax.swing.text.Style;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId(){
        return carId;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{

    private String customerId;

    private String name;

    private String address;

    public Customer(String customerId, String name,String address){
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getName(){
        return name;
    }

    public String address(){
        return address;
    }

}

class Rental{
    private Car car;

    private Customer customer;

    private int days;

    public Rental(Car car, Customer customer,int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }

}

class CarRentalSystem{

    private List<Car> cars;

    private List<Customer> customers;

    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }else {
            System.out.println("Car is not available at the moment for rent.");
        }
    }

    public void returnCar( Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for( Rental rental: rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("Car returned Successfully!!");
        }else{
            System.out.println("Car was not returned!!");
        }
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("------ Car Rental System -------");
            System.out.println("1. Rent a car ");
            System.out.println("2. Return a car ");
            System.out.println("3. Exit ");
            System.out.println("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1) {
                System.out.println("------ Rent a Car -------");
                System.out.println("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable Cars: ");
                for( Car car: cars){
                    if(car.isAvailable()) {
                        System.out.println(car.getCarId()+ " - " + car.getBrand() + " - " + car.getModel());
                    }
                }
                System.out.println("\n Enter the car ID you want to rent: ");
                String carId = sc.nextLine();

                System.out.println("\n Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName, "Default Address");
                addCustomer(newCustomer);

                Car selectedCar =  null;
                for(Car car: cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n == Rental Information == \n");
                    System.out.println("Customer ID : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name  : " + newCustomer.getName());
                    System.out.println("Customer Address : " + newCustomer.address());
                    System.out.println("Rental Dyas: " + rentalDays);
                    System.out.printf("Total Price : $%.2f%n", totalPrice);
                    
                    System.out.println("\nConfirm rental (Y/N) : ");
                    String confirm =  sc.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n Car Rented Successfully");
                    }else {
                        System.out.println("\n\nRental cancelled");
                    }

                } else {
                    System.out.println("\nInvalid car Selection or car not available for rent");
                }
            }else if (choice == 2) {
                System.out.println("\n == Return a Car == \n");
                System.out.println("\n Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car: cars){
                    if(car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null) {
                    Customer customer = null;
                    for(Rental rental : rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if(customer != null) {
                        returnCar(carToReturn);
                        System.out.println("\nCar returned successfully by " + customer.getName());
                    }else {
                        System.out.println("\nCar was not rented or rental information is mising");
                    }
                }else {
                    System.out.println("\nInvalid carID or car is not rented.");
                }
            }else if (choice == 3){
                break;
            }else {
                System.out.println("\n Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThankyou for using the Car Rental System!");
        sc.close();
    }
    

}

public class Main{
    public static void main(String[] args){
        CarRentalSystem cs = new CarRentalSystem();

        Car c1 = new Car("C001", "Toyota", "Camry" , 60.0);
        Car c2 = new Car("C002", "Mahindra", "Fortuner" , 500.0);
        Car c3 = new Car("C003", "Honda", "Audi" , 80.0);
        cs.addCar(c1);
        cs.addCar(c2);
        cs.addCar(c3);


        cs.menu();
    }
}