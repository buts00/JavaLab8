import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Custom exception class for handling appointment-related exceptions.
 */
class AppointmentException extends Exception {
    public AppointmentException(String message) {
        super(message);
    }
}

/**
 * Represents a Dentist and manages their appointments.
 */

class Dentist {
    private static Map<LocalTime, LocalTime> scheduledAppointments = new HashMap<>();
    private static final LocalTime WORK_HOURS_START = LocalTime.of(8, 0);
    private static final LocalTime WORK_HOURS_END = LocalTime.of(17, 0);
    private static final LocalTime LUNCH_START = LocalTime.of(12, 0);
    private static final LocalTime LUNCH_END = LocalTime.of(13, 0);
    private static final int APPOINTMENT_DURATION = 1;


    /**
     * Attempts to make an appointment at the requested time.
     *
     * @param requestedTime The time for the requested appointment.
     * @throws AppointmentException If the appointment cannot be scheduled.
     */
    public static void makeAppointment(LocalTime requestedTime) throws AppointmentException {
        LocalTime endTime = requestedTime.plusHours(APPOINTMENT_DURATION);
        if( requestedTime.isBefore(WORK_HOURS_START) || endTime.isAfter(WORK_HOURS_END) ||
                (requestedTime.isAfter(LUNCH_START) && requestedTime.isBefore(LUNCH_END)) ||
                (endTime.isAfter(LUNCH_START) && endTime.isBefore(LUNCH_END)) ||
                (requestedTime.equals(LUNCH_START) || !isAppointmentPossible(requestedTime) )
        ) {
            throw new AppointmentException("Лікар не може в цей час!");
        }


        scheduledAppointments.put(requestedTime, endTime);
        System.out.println("Запис збережено " + requestedTime);
    }

    /**
     * Displays the list of scheduled appointments.
     */
    public static void showScheduledAppointments() {
        if (scheduledAppointments.isEmpty()) {
            System.out.println("Записів немає.");
        } else {
            System.out.println("Зайняті години:");
            for (Map.Entry<LocalTime, LocalTime> entry : scheduledAppointments.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }
    }


    /**
     * Checks if an appointment is possible at the requested time.
     *
     * @param requestedTime The requested time for an appointment.
     * @return True if an appointment is possible, false otherwise.
     */

    public static boolean isAppointmentPossible(LocalTime requestedTime) {
        LocalTime endTime = requestedTime.plusHours(APPOINTMENT_DURATION);

        for (Map.Entry<LocalTime, LocalTime> entry : scheduledAppointments.entrySet()) {
            LocalTime existingTime = entry.getKey();
            LocalTime existingEndTime = entry.getValue();

            if ((requestedTime.isAfter(existingTime) && requestedTime.isBefore(existingEndTime)) ||
                    (endTime.isAfter(existingTime) && endTime.isBefore(existingEndTime)) ||
                    requestedTime.equals(existingTime)) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Main class to run the appointment scheduling program.
 */

public class Main {
    /**
     * Main method to start the appointment scheduling program.
     *
     * @param args Command-line arguments (not used in this program).
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean fl = true;
        while (fl) {
            System.out.println("Меню:");
            System.out.println("1.Подивитись зайняті години");
            System.out.println("2.Додати запис до лікаря");
            System.out.println("3.Вийти з програми");
            System.out.print("Оберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Dentist.showScheduledAppointments();
                    break;
                case 2:
                    try {
                        System.out.println("Введіть години для запису (години та хвилини):");
                        int hour = scanner.nextInt();
                        int minute = scanner.nextInt();
                        Dentist.makeAppointment(LocalTime.of(hour, minute));
                    } catch (AppointmentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("До побачення!");
                    fl = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте знову.");
            }
        }
        scanner.close();
    }
}
