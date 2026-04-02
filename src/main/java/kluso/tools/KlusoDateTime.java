package kluso.tools;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Instances of this object process date times for the kluso.object.Event and kluso.object.Deadline
 * classes
 */
public class KlusoDateTime {

    private static final DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter INPUT_TIME_FORMAT =
            DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");
    private static final int SLASHES_DATE_ONLY = 0;
    private static final int SLASHES_DATE_AND_TIME = 1;
    private static final int TIME_STRING_LENGTH = 4;

    private final LocalDateTime dateTime;
    private final boolean hasTime;
    private final String input;


    /**
     * Kluso Date time constructor
     * @param input for other methods to pass in either "dd-mm-yyyy" or "dd-mm-yyyy/0000"
     */
    public KlusoDateTime(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Date/time is empty! Give me <dd-mm-yyyy/0000> date/time," +
                    "or just <dd-mm-yyyy>!");
        }

        int slashes = countSlashes(input);

        if (slashes == SLASHES_DATE_ONLY) {
            this.dateTime = parseDate(input).atStartOfDay();
            this.hasTime = false;
            this.input = input;
        } else if (slashes == SLASHES_DATE_AND_TIME) {
            int dateTimeDividerIndex = input.indexOf("/");
            String date = input.substring(0, dateTimeDividerIndex);
            String time = input.substring(dateTimeDividerIndex + 1);

            this.dateTime = parseDate(date).atTime(parseTime(time));
            this.hasTime = true;
            this.input = input;
        } else {
            throw new IllegalArgumentException(
                    "You've given me a bad date/time format! Give me <dd-mm-yyyy/0000> date/time," +
                            "or just <dd-mm-yyyy>!");
        }
    }

    // Simple slash counter
    private static int countSlashes(String input) {
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == '/') {
                count = count + 1;
            }
        }
        return count;
    }

    // Simple date string parser
    private static java.time.LocalDate parseDate(String date) {
        try {
            return java.time.LocalDate.parse(date, INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "\"" + date + "\" isn't a valid date! Give me something like " +
                            "dd-mm-yyyy as part of your comms!");
        }
    }

    // Simple time string parser
    private static LocalTime parseTime(String time) {
        boolean isRightLength = (time.length() == TIME_STRING_LENGTH);
        //asked Gemini AI for easy method to implement this
        boolean charactersAreDigits = (time.chars().allMatch(Character::isDigit));

        if (!isRightLength || !charactersAreDigits) {
            throw new IllegalArgumentException(
                    "\"" + time + "\" is an invalid time, give me military time, which is " +
                            "something like 0130 or 2359"
            );
        }

        try {
            return LocalTime.parse(time, INPUT_TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "\"" + time + "\" is an invalid time, give me military time, which is " +
                            "something like 0130 or 2359"
            );
        }

    }

    //------------- getter classes-------------//

    public String getInput() {
        return input;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        String displayDate = dateTime.format(DISPLAY_DATE_FORMAT);

        if(!hasTime) {
            return displayDate;
        }

        return displayDate + ", " + dateTime.format(DISPLAY_TIME_FORMAT);
    }



}
