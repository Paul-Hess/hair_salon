import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.util.Date;

public class ClientTest {

	private final Client testClient = new Client("test client", 0);

	@Rule
  public DatabaseRule database = new DatabaseRule();

  @Test 
  public void client_instantiatesCorrectly_true() {
  	assertTrue(testClient instanceof Client);
  }

  // @Test 
  // public void getName_returnsClientName_String() {
  // 	assertEquals(testClient.getName(), "test client");
  // }

  // @Test 
  // public void getId_returnsClientId_int() {
  // 	assertEquals(testClient.getId(), 0);
  // }

  // @Test 
  // public void getCreatedAt_returnsCreatedAt_Timestamp() {
  // 	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  // 	assertEquals(testClient.getCreatedAt().getHours(), testTimestamp.getHours());
  // }

  // @Test 
  // public void getUpdatedAt_instantiatesSameAsCreatedAt_Timestamp() {
  // 	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  // 	assertEquals(testClient.getUpdatedAt().getHours(), testTimestamp.getHours());
  // }

  // @Test 
  // public void getStylistId_returnsAssociatedStylistId_int() {
  // 	assertEquals(testClient.getStylistId(), 0);
  // }
}