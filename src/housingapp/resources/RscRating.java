package housingapp.resources;

import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RscRating {

    public static Map<String, ArrayList<Rating>> getRatings() {
        Map<String, ArrayList<Rating>> ratings = new HashMap<String, ArrayList<Rating>>();
        ArrayList<Rating> propertyRatings = new ArrayList<Rating>();
        ArrayList<Rating> studentRatings = new ArrayList<Rating>();
        try {
            // read users array from JSON file
            FileReader reader = new FileReader(SysConst.RATINGS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONObject ratingsFileJSON = (JSONObject) parser.parse(reader);

            // pull students and property managers lists from file
            JSONArray propertyRatingsJSON = (JSONArray) ratingsFileJSON.get(SysConst.PROPERTY_RATINGS);
            JSONArray studentRatingsJSON = (JSONArray) ratingsFileJSON.get(SysConst.STUDENT_USER_RATINGS);

            // parse property ratings list
            if (propertyRatingsJSON != null) {
                for (int i=0; i<propertyRatingsJSON.size(); i++) {
                    JSONObject propertyRatingJSON = (JSONObject) propertyRatingsJSON.get(i);
                    UUID ratingId = (UUID) propertyRatingJSON.get(SysConst.RATING_ID);
                    int stars = (int) propertyRatingJSON.get(SysConst.RATING_STARS);
                    String comment = (String) propertyRatingJSON.get(SysConst.RATING_COMMENT);

                    // property rating-unique attributes
                    int valueStars = (int) propertyRatingJSON.get(SysConst.PROPERTY_RATING_VALUE_STARS);
                    int managementStars = (int) propertyRatingJSON.get(SysConst.PROPERTY_RATING_MANAGEMENT_STARS);
                    int neighborhoodStars = (int) propertyRatingJSON.get(SysConst.PROPERTY_RATING_NEIGHBORHOOD_STARS);

                    // add property rating to propertyRatings list;
                    propertyRatings.add(new PropertyRating(ratingId, stars, comment, valueStars, managementStars, neighborhoodStars));
                }
                ratings.put(SysConst.PROPERTY_RATINGS, propertyRatings);
            }

            // parse student ratings list
            if (studentRatingsJSON != null) {
                for (int i=0; i<studentRatingsJSON.size(); i++) {
                    JSONObject studentRatingJSON = (JSONObject) studentRatingsJSON.get(i);
                    UUID ratingId = (UUID) studentRatingJSON.get(SysConst.RATING_ID);
                    int stars = (int) studentRatingJSON.get(SysConst.RATING_STARS);
                    String comment = (String) studentRatingJSON.get(SysConst.RATING_COMMENT);

                    // student rating-unique attributes
                    int numLatePayments = (int) studentRatingJSON.get(SysConst.STUDENT_RATING_NUM_LATE_PAYMENTS);
                    double damagesValue = (double) studentRatingJSON.get(SysConst.STUDENT_RATING_DAMAGES_VALUE);

                    // add student rating to studentRatings list;
                    studentRatings.add(new StudentRating(ratingId, stars, comment, numLatePayments, damagesValue));
                }
                ratings.put(SysConst.STUDENT_USER_RATINGS, studentRatings);
            }

            return ratings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeRatings() {
        ResourceManager rm = ResourceManager.getInstance();

        // first, populate propertyRatings JSONArray
        ArrayList<Rating> propertyRatings = rm.getPropertyRatings();
        JSONArray propertyRatingsJSON = new JSONArray();
        for (int i=0; i<propertyRatings.size(); i++) {
            propertyRatingsJSON.add(getPropertyRatingJSON((PropertyRating) propertyRatings.get(i)));
        }

        // second, populate studentRatings JSONArray
        ArrayList<Rating> studentRatings = rm.getStudentRatings();
        JSONArray studentRatingsJSON = new JSONArray();
        for (int i=0; i<studentRatings.size(); i++) {
            studentRatingsJSON.add(getStudentRatingJSON((StudentRating) studentRatings.get(i)));
        }

        // third, create ratingsJSON object
        JSONObject ratingsJSON = new JSONObject();
        ratingsJSON.put(SysConst.PROPERTY_RATINGS, propertyRatingsJSON);
        ratingsJSON.put(SysConst.STUDENT_USER_RATINGS, studentRatingsJSON);

        // last, write to data file
        try (FileWriter writer = new FileWriter(SysConst.RATINGS_DATA_FILE)) {
            writer.write(ratingsJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getPropertyRatingJSON(PropertyRating propertyRating) {
        JSONObject propertyRatingJSON = new JSONObject();
        propertyRatingJSON.put(SysConst.RATING_STARS, propertyRating.getStars());
        propertyRatingJSON.put(SysConst.RATING_COMMENT, propertyRating.getComment());

        propertyRatingJSON.put(SysConst.PROPERTY_RATING_VALUE_STARS, propertyRating.getValueStars());
        propertyRatingJSON.put(SysConst.PROPERTY_RATING_MANAGEMENT_STARS, propertyRating.getManagementStars());
        propertyRatingJSON.put(SysConst.PROPERTY_RATING_NEIGHBORHOOD_STARS, propertyRating.getNeighborhoodStars());

        return propertyRatingJSON;
    }

    public static JSONObject getStudentRatingJSON(StudentRating studentRating) {
        JSONObject studentRatingJSON = new JSONObject();
        studentRatingJSON.put(SysConst.RATING_STARS, studentRating.getStars());
        studentRatingJSON.put(SysConst.RATING_COMMENT, studentRating.getComment());

        studentRatingJSON.put(SysConst.STUDENT_RATING_NUM_LATE_PAYMENTS, studentRating.getNumLatePayments());
        studentRatingJSON.put(SysConst.STUDENT_RATING_DAMAGES_VALUE, studentRating.getDamagesValue());

        return studentRatingJSON;
    }
}
