import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import java.sql.Timestamp;

public class VisitTest {

	@Rule
  public DatabaseRule database = new DatabaseRule();

  private final Visit testVisit = new Visit(0, 1, "description", "review");

  @Test 
  public void visit_instantiatesCorrectly_true() {
  	assertTrue(testVisit instanceof Visit);
  }	

  @Test 
  public void getDateFromString_setsDateTimeToTimestamp_timesatamp() {
    Timestamp testTimestamp = new Timestamp(new Date().getTime());
    String inputTime = "2016-05-10 08:45:00:00";
    Visit testVisit = new Visit(1, 0, "description", "review");
    testVisit.getDateFromString(inputTime);
    assertEquals(testVisit.getVisitDate().getDay(), testTimestamp.getDay());
  }

  @Test 
  public void getStylistId_returnsStylistId_0() {
  	assertEquals(testVisit.getStylistId(), 0);
  }

  @Test 
  public void getClientId_returnsClientId_1() {
  	assertEquals(testVisit.getClientId(), 1);
  }

  @Test 
  public void getDescription_returnsStyleDescription_string() {
  	assertEquals(testVisit.getDescription(), "description");
  }

  @Test 
  public void getREview_returnsStyleREivew_string() {
  	assertEquals(testVisit.getReview(), "review");
  }

  @Test 
  public void all_instantiatesAsEmptyList_0() {
  	assertEquals(Visit.all().size(), 0);
  }

  @Test 
  public void equals_overridesAndReturnsTrueWhenPropertiesAreTheSame_true() {
  	Visit testVisitTwo = new Visit(0, 1, "description", "review");
  	assertEquals(testVisit, testVisitTwo);
  }

  @Test 
  public void schedule_savesVisitCorrectly_true() {
  	Stylist testStylist = new Stylist("test name", "specialty", "example.wav");
  	testStylist.save();
  	Client testClient = new Client("test name", testStylist.getId());
  	testClient.save();
  	Visit newTestVisit = new Visit(testStylist.getId(), testClient.getId(), "foo", "bar");
    String inputTime = "2016-05-10 08:45:00:00";
    newTestVisit.getDateFromString(inputTime);
  	newTestVisit.schedule();
  	assertTrue(Visit.all().get(0).equals(newTestVisit));
  }

  @Test 
  public void find_findsSpecificInstanceOfVisit__visit() {
  	Stylist testStylist = new Stylist("test name", "specialty", "example.wav");
  	testStylist.save();
  	Client testClient = new Client("test name", testStylist.getId());
  	testClient.save();
    String inputTime = "2016-05-10 08:45:00:00";
    Visit newTestVisit = new Visit(testStylist.getId(), testClient.getId(), "foo", "bar");
    newTestVisit.getDateFromString(inputTime);
    Timestamp testTimestamp = newTestVisit.getVisitDate();
  	newTestVisit.schedule();
  	assertEquals(Visit.find(testStylist.getId(), testClient.getId(), testTimestamp), newTestVisit);
  }

  @Test 
  public void reSchedule_resetsDatetimeproperty_timestamp() {
  	Stylist testStylist = new Stylist("test name", "specialty", "example.wav");
  	testStylist.save();
  	Client testClient = new Client("test name", testStylist.getId());
  	testClient.save();
		Visit newTestVisit = new Visit(testStylist.getId(), testClient.getId(), "foo", "bar");
    String inputTime = "2016-05-10 06:45:00:00";
    newTestVisit.getDateFromString(inputTime);
    newTestVisit.schedule();
    Timestamp testTimestamp = newTestVisit.getVisitDate();
    String inputTime2 = "2016-05-11 08:45:00:00";
		newTestVisit.reSchedule(inputTime2);
		Visit updatedVisit = Visit.find(newTestVisit.getStylistId(), newTestVisit.getClientId(), newTestVisit.getVisitDate());
		assertTrue(updatedVisit.getVisitDate().after(testTimestamp));
  }

  @Test 
  public void remove() {
    String inputTime = "2016-05-10 06:45:00:00";
    testVisit.getDateFromString(inputTime);
  	testVisit.schedule();
  	testVisit.remove();
  	assertEquals(Visit.all().size(), 0);
  }
}