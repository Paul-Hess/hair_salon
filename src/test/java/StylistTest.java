import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;

public class StylistTest {
	private final Stylist testStylist = new Stylist("stylist name", "specialty", "example.jpg");
	@Rule
  public DatabaseRule database = new DatabaseRule();

  @Test 
  public void Stylist_instantiatessCorrectly_true() {
  	assertTrue(testStylist instanceof Stylist);
  }

  @Test
  public void getName_returnStylistName_String() {
  	assertEquals(testStylist.getName(), "stylist name");
  }

  @Test 
  public void getSpecialty_returnStylistSpecialty_String() {
  	assertEquals(testStylist.getSpecialty(), "specialty");
  }	

  @Test 
  public void getImage_returnStylistImageUrl_String() {
  	assertEquals(testStylist.getImage(), "example.jpg");
  }

  // @Test 
  // public void getId_returnStylistId_int() {
  // 	assertEquals(testStylist.getId(), 0);
  // }
	
	// @Test 
	// public void getCreatedAt_returnStylistCreatedAt_Timestamp() {
	// 	Timestamp testTimestamp = new Timestamp(new Date().getTime());
	// 	assertEquals(testTimestamp.getHours(), testStylist.getCreatedAt().getHours());
	// }

	// @Test 
	// public void getUpdatedAt_returnStylistUpdatedAt_Timestamp() {
	// 	Timestamp testTimestamp = new Timestamp(new Date().getTime());
	// 	assertEquals(testTimestamp.getHours(), testStylist.getUpdatedAt().getHours());
	// }



}