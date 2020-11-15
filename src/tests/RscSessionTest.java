package tests;

import housingapp.Session;
import housingapp.SysConst;
import housingapp.query.ResourceManager;
import housingapp.resources.RscSession;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class RscSessionTest {

    private Session session = TestConst.SESSION_VALID;
    private Session nullUserSession = TestConst.SESSION_NULL_USER;
    private static ResourceManager rm = ResourceManager.getInstance();

    @BeforeClass
    public static void oneTimeSetup() {

    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
        // clear sessions data file
        rm.clearSessions();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetSessionsEmpty() {
        ArrayList<Session> sessions = RscSession.getSessions();
        assertNotNull(sessions);
        assertEquals(sessions.size(), 0);
    }

    @Test
    public void testGetSessionsPopulated() {
        rm.addSession(session);
        ArrayList<Session> sessions = RscSession.getSessions();
        assertNotNull(sessions);
        assertEquals(sessions.size(), 1);
        assertEquals(sessions.get(0).getToken(), session.getToken());
    }

    @Test
    public void testWriteSessionsEmpty() {
        rm.clearSessions();
        RscSession.writeSessions();
        ArrayList<Session> sessions = RscSession.getSessions();
        assertNotNull(sessions);
        assertEquals(sessions.size(), 0);
    }

    @Test
    public void testWriteSessionsPopulated() {
        rm.addSession(session);
        RscSession.writeSessions();
        ArrayList<Session> sessions = RscSession.getSessions();
        assertNotNull(sessions);
        assertEquals(sessions.size(), 1);
        assertEquals(sessions.get(0).getToken(), session.getToken());
    }

    @Test
    public void testGetValidSessionJSON() {
        JSONObject validSessionJSON = RscSession.getSessionJSON(session);

        assertEquals(UUID.fromString((String) validSessionJSON.get(SysConst.SESSION_TOKEN)), session.getToken());
        assertEquals(UUID.fromString((String) validSessionJSON.get(SysConst.SESSION_USER_ID)), session.getUserId());
        assertEquals(validSessionJSON.get(SysConst.SESSION_EXPIRATION), session.getExpiration().toString());
    }

    @Test
    public void testGetNullUserSessionJSON() {
        JSONObject nullUserSessionJSON = RscSession.getSessionJSON(nullUserSession);

        assertNull(nullUserSessionJSON.get(SysConst.SESSION_USER_ID));
    }
}
