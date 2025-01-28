import java.util.Date;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        // Добавим пример пользователя
        User doctor = new Doctor(1, "Dr. Smith", "dr.smith@example.com", "9876543210", "123 Main St", "Cardiology");
        dbManager.addUser(doctor);

        // Добавим пример записи на прием
        Appointment appointment = new Appointment(1, 1, 1, new Date(), "Routine check-up");
        // Предполагается, что у вас есть логика для добавления приема в базу данных

        // Добавим оплату за прием
        Payment payment = new Payment(1, 1, 200.00, new Date(), "Credit", "Completed");
        dbManager.addPayment(payment);

        // Получим и выведем все записи об оплатах
        dbManager.fetchPayments();
    }
}
