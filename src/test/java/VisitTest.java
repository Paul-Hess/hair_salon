import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import java.sql.Timestamp;

public class VisitTest {

	@Rule
  public DatabaseRule database = new DatabaseRule();

  private final Visit testVisit = new Visit(0, 1, "description", "review", new Timestamp(new Date().getTime()));

  @Test 
  public void visit_instantiatesCorrectly_true() {
  	assertTrue(testVisit instanceof Visit);
  }	

  @Test 
  public void getId_returnsInstanceId_id() {
  	assertEquals(testVisit.getId(), 0);
  }

  @Test 
  public void getDate_returnsDateTimeOfVisit_timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testTimestamp.getHours(), testVisit.getVisitDate().getHours());
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
  	Visit testVisitTwo = new Visit(0, 1, "description", "review", new Timestamp(new Date().getTime()));
  	assertEquals(testVisit, testVisitTwo);
  }

  @Test 
  public void schedule_savesVisitCorrectly_true() {
  	Stylist testStylist = new Stylist("test name", "specialty", "example.wav");
  	testStylist.save();
  	Client testClient = new Client("test name", testStylist.getId());
  	testClient.save();
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	Visit newTestVisit = new Visit(testStylist.getId(), testClient.getId(), "foo", "bar", testTimestamp);
  	newTestVisit.schedule();
  	assertTrue(Visit.all().get(0).equals(newTestVisit));
  }
}