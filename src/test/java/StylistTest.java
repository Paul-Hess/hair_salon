import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StylistTest {

	@Rule
  public DatabaseRule database = new DatabaseRule();

  @Test 
  public void Stylist_instantiatessCorrectly_true() {
  	Stylist testStylist = new Stylist("stylist name", "specialty");
  	assertTrue(testStylist instanceof Stylist);
  }
	
}