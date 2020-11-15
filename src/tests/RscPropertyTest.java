package tests;

import housingapp.SysConst;
import housingapp.housing.Property;
import housingapp.query.ResourceManager;
import housingapp.resources.RscProperty;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class RscPropertyTest {

    private Property property = TestConst.PROPERTY_VALID;
    private Property emptyProperty = TestConst.PROPERTY_EMPTY;
    private static ResourceManager rm = ResourceManager.getInstance();

    @BeforeClass
    public static void oneTimeSetup() {

    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
        // clear properties data file
        rm.clearProperties();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetPropertiesEmpty() {
        ArrayList<Property> properties = RscProperty.getProperties();
        assertNotNull(properties);
        assertEquals(properties.size(), 0);
    }

    @Test
    public void testGetPropertiesPopulated() {
        rm.addProperty(property);
        ArrayList<Property> properties = RscProperty.getProperties();
        assertNotNull(properties);
        assertEquals(properties.size(), 1);
        assertEquals(properties.get(0).getId(), property.getId());
    }

    @Test
    public void testWritePropertiesEmpty() {
        rm.clearProperties();
        RscProperty.writeProperties();
        ArrayList<Property> properties = RscProperty.getProperties();
        assertNotNull(properties);
        assertEquals(properties.size(), 0);
    }

    @Test
    public void testWritePropertiesPopulated() {
        rm.addProperty(property);
        RscProperty.writeProperties();
        ArrayList<Property> properties = RscProperty.getProperties();
        assertNotNull(properties);
        assertEquals(properties.size(), 1);
        assertEquals(properties.get(0).getId(), property.getId());
    }

    @Test
    public void testGetValidPropertyJSON() {
        JSONObject validPropertyJSON = RscProperty.getPropertyJSON(property);

        assertEquals(UUID.fromString((String) validPropertyJSON.get(SysConst.PROPERTY_ID)), property.getId());
        assertEquals(UUID.fromString((String) validPropertyJSON.get("landlordId")), property.getLandlordId());
        assertEquals(validPropertyJSON.get(SysConst.PROPERTY_NAME), property.getName());
        assertEquals(validPropertyJSON.get(SysConst.PROPERTY_ADDRESS), property.getAddress());
        assertEquals(validPropertyJSON.get("zipCode"), property.getZipCode());
        assertEquals(validPropertyJSON.get(SysConst.PROPERTY_DISTANCE_TO_CAMPUS), property.getDistanceToCampus());
        assertEquals(validPropertyJSON.get("damagesCost"), property.getDamagesCost());
        assertEquals(validPropertyJSON.get("furnished"), property.isFurnished());
        assertEquals(validPropertyJSON.get("petsAllowed"), property.petsAllowed());
        assertEquals(validPropertyJSON.get("hasPool"), property.hasPool());
        assertEquals(validPropertyJSON.get("hasGym"), property.hasGym());
        assertEquals(validPropertyJSON.get("hasFreeWifi"), property.hasFreeWifi());
    }

    @Test
    public void testGetEmptyPropertyJSON() {
        JSONObject nullPropertyJSON = RscProperty.getPropertyJSON(emptyProperty);

        assertNull(nullPropertyJSON.get("landlordId"));
        assertEquals(nullPropertyJSON.get(SysConst.PROPERTY_NAME), "");
        assertEquals(nullPropertyJSON.get(SysConst.PROPERTY_ADDRESS), "");
        assertEquals(nullPropertyJSON.get("zipCode"), "");
        assertEquals(nullPropertyJSON.get(SysConst.PROPERTY_DISTANCE_TO_CAMPUS), 0.0);
        assertEquals(nullPropertyJSON.get("damagesCost"), 0.0);
        assertEquals(nullPropertyJSON.get("furnished"), false);
        assertEquals(nullPropertyJSON.get("petsAllowed"), false);
        assertEquals(nullPropertyJSON.get("hasPool"), false);
        assertEquals(nullPropertyJSON.get("hasGym"), false);
        assertEquals(nullPropertyJSON.get("hasFreeWifi"), false);
    }
}
