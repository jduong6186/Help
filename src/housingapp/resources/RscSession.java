package housingapp.resources;

import housingapp.query.ResourceManager;
import housingapp.Session;
import housingapp.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * handles JSON read/write functionality for session resources
 */

public class RscSession {

    /**
     * reads sessions from sessions.json data file, returns an array list of sessions
     */
    public static ArrayList<Session> getSessions() {
        ArrayList<Session> sessions = new ArrayList<Session>();
        try {
            // read sessions array from JSON file
            FileReader reader = new FileReader(SysConst.SESSIONS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONArray sessionsJSON = (JSONArray) parser.parse(reader);

            // parse individual session objects from JSONArray
            if (sessionsJSON != null) {
                for (int i=0; i<sessionsJSON.size(); i++) {
                    JSONObject sessionJSON = (JSONObject) sessionsJSON.get(i);
                    UUID token = UUID.fromString((String) sessionJSON.get(SysConst.SESSION_TOKEN));
                    UUID userId = UUID.fromString((String) sessionJSON.get(SysConst.SESSION_USER_ID));
                    String expirationStr = (String) sessionJSON.get(SysConst.SESSION_EXPIRATION);
                    // yyyy-mm-dd HH:mm:ss
                    String[] expirationParts = expirationStr.split("T");
                    String expirationDateStr = expirationParts[0];
                    String expirationTimeStr = expirationParts[1];

                    String[] expirationDateParts = expirationDateStr.split("-");
                    int expirationYear = Integer.parseInt(expirationDateParts[0]);
                    int expirationMonth = Integer.parseInt(expirationDateParts[1]);
                    int expirationDay = Integer.parseInt(expirationDateParts[2]);

                    String[] expirationTimeParts = expirationTimeStr.split(":");
                    int expirationHour = Integer.parseInt(expirationTimeParts[0]);
                    int expirationMinute = Integer.parseInt(expirationTimeParts[1]);
                    int expirationSecond = (int) Math.floor(Double.parseDouble(expirationTimeParts[2]));

                    LocalDateTime expiration = LocalDateTime.of(LocalDate.of(expirationYear, expirationMonth, expirationDay), LocalTime.of(expirationHour, expirationMinute, expirationSecond));
                    // append session to sessions
                    sessions.add(new Session(token, userId, expiration));
                }
            }
            return sessions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * writes updated sessions from resource managers to sessions.json data file
     */
    public static void writeSessions() {
        ResourceManager rm = ResourceManager.getInstance();
        ArrayList<Session> sessions = rm.getSessions();
        JSONArray sessionsJSON = new JSONArray();
        if (sessions != null) {
            for (int i=0; i<sessions.size(); i++) {
                sessionsJSON.add(getSessionJSON(sessions.get(i)));
            }
        }
        try (FileWriter writer = new FileWriter(SysConst.SESSIONS_DATA_FILE)) {
            writer.write(sessionsJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructs JSON representation of session
     * @param session instance of a session
     */
    public static JSONObject getSessionJSON(Session session) {
        JSONObject sessionJSON = new JSONObject();
        UUID sessionToken = session.getToken();
        if (sessionToken != null) {
            sessionJSON.put(SysConst.SESSION_TOKEN, sessionToken.toString());
        }
        UUID sessionUserId = session.getUserId();
        if (sessionUserId != null) {
            sessionJSON.put(SysConst.SESSION_USER_ID, sessionUserId.toString());
        }
        LocalDateTime sessionExpiration = session.getExpiration();
        if (sessionExpiration != null) {
            sessionJSON.put(SysConst.SESSION_EXPIRATION, sessionExpiration.toString());
        }
        return sessionJSON;
    }
}
