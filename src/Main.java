import java.util.Date;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();


        User doctor = new Doctor(1, "Dr. Smith", "dr.smith@example.com", "9876543210", "123 Main St", "Cardiology");
        dbManager.addUser(doctor);


        Appointment appointment = new Appointment(1, 1, 1, new Date(), "Routine check-up");



        Payment payment = new Payment(1, 1, 200.00, new Date(), "Credit", "Completed");
        dbManager.addPayment(payment);

        dbManager.fetchPayments();
    }
}
