package housingapp.resources;

import housingapp.ResourceManager;
import housingapp.Session;
import housingapp.system.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class RscSession {

    public static ArrayList<Session> getSessions() {
        ArrayList<Session> sessions = new ArrayList<Session>();
        try {
            // read sessions array from JSON file
            FileReader reader = new FileReader(SysConst.SESSIONS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONArray sessionsJSON = (JSONArray) parser.parse(reader);

            // parse individual session objects from JSONArray
            for (int i=0; i<sessionsJSON.size(); i++) {
                JSONObject sessionJSON = (JSONObject) sessionsJSON.get(i);
                UUID token = (UUID) sessionJSON.get(SysConst.SESSION_TOKEN);
                UUID userId = (UUID) sessionJSON.get(SysConst.SESSION_USER_ID);
                LocalDateTime expiration = (LocalDateTime) sessionJSON.get(SysConst.SESSION_EXPIRATION);

                // append session to sessions
                sessions.add(new Session(token, userId, expiration));
            }
            return sessions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeSessions() {
        ResourceManager rm = ResourceManager.getInstance();
        ArrayList<Session> sessions = rm.getSessions();
        JSONArray sessionsJSON = new JSONArray();
        for (int i=0; i<sessions.size(); i++) {
            sessionsJSON.add(getSessionJSON(sessions.get(i)));
        }
        try (FileWriter writer = new FileWriter(SysConst.SESSIONS_DATA_FILE)) {
            writer.write(sessionsJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getSessionJSON(Session session) {
        JSONObject sessionJSON = new JSONObject();
        sessionJSON.put(SysConst.SESSION_TOKEN, session.getToken());
        sessionJSON.put(SysConst.SESSION_USER_ID, session.getUserId());
        sessionJSON.put(SysConst.SESSION_EXPIRATION, session.getExpiration());
        return sessionJSON;
    }
}
