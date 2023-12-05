import java.time.LocalTime;
/**
 * A class representing an exception that occurs when attempting to schedule an appointment with a dentist.
 */
class AppointmentException extends Exception {
    /**
     * Constructs an AppointmentException with a specified error message.
     *
     * @param message the error message
     */
    public AppointmentException(String message) {
        super(message);
    }
}

/**
 * A class representing a dentist and allowing appointments to be scheduled.
 */
class Dentist {
    private static final int WORK_HOURS_START = 8;
    private static final int WORK_HOURS_END = 17;
    private static final int LUNCH_START = 12;
    private static final int LUNCH_END = 13;
    private static final int APPOINTMENT_DURATION = 1;

    /**
     * Attempts to schedule an appointment at the requested time.
     *
     * @param requestedTime the time for which the appointment is requested
     * @throws AppointmentException if the requested time is outside working hours or during lunch break
     */
    public static void makeAppointment(LocalTime requestedTime) throws AppointmentException {
        if (requestedTime.getHour() < WORK_HOURS_START ||
                requestedTime.getHour() + APPOINTMENT_DURATION > WORK_HOURS_END ||
                (requestedTime.getHour() >= LUNCH_START && requestedTime.getHour() < LUNCH_END)) {
            throw new AppointmentException("Doctor is not available at this time");
        }

        System.out.println("Appointment scheduled at " + requestedTime);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            Dentist.makeAppointment(LocalTime.of(10, 0));
            Dentist.makeAppointment(LocalTime.of(13, 30));
            Dentist.makeAppointment(LocalTime.of(18, 0));
        } catch (AppointmentException e) {
            System.out.println("Unable to schedule appointment: " + e.getMessage());
        }
    }
}
