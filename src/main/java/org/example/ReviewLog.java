package org.example;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
public class ReviewLog {
    Rating rating;
    int scheduledDays;
    int elapsedDays;
    LocalDateTime reviewDateTime;
    State cardState;

    public ReviewLog(Rating rating,
                     int scheduledDays,
                     int elapsedDays,
                     LocalDateTime reviewDateTime,
                     State cardState) {
        this.rating = rating;
        this.scheduledDays = scheduledDays;
        this.elapsedDays = elapsedDays;
        this.reviewDateTime = reviewDateTime;
        this.cardState = cardState;
    }

    public Map<String, Object> toDict() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("rating", rating.getValue()); // Assuming getValue() returns the appropriate value
        returnMap.put("scheduledDays", scheduledDays);
        returnMap.put("elapsedDays", elapsedDays);
        returnMap.put("reviewDateTime", reviewDateTime.toString()); // ISO format
        returnMap.put("cardState", cardState.getValue()); // Assuming getValue() returns the appropriate value
        return returnMap;
    }

    public static ReviewLog fromDict(Map<String, Object> sourceDict) {
        Rating rating = Rating.values()[Integer.parseInt(sourceDict.get("rating").toString())];
        int scheduledDays = Integer.parseInt(sourceDict.get("scheduledDays").toString());
        int elapsedDays = Integer.parseInt(sourceDict.get("elapsedDays").toString());
        LocalDateTime review = LocalDateTime.parse((String) sourceDict.get("reviewDateTime"), DateTimeFormatter.ISO_DATE);
        State state = State.values()[Integer.parseInt(sourceDict.get("cardState").toString())];
        return new ReviewLog(rating, scheduledDays, elapsedDays, review, state);
    }

}
