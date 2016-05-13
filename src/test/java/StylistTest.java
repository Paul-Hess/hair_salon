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


  @Test 
  public void getId_returnStylistId_int() {
  	assertEquals(testStylist.getId(), 0);
  }
	
	@Test 
	public void getCreatedAt_returnStylistCreatedAt_Timestamp() {
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testTimestamp.getHours(), testStylist.getCreatedAt().getHours());
	}

	@Test 
	public void getUpdatedAt_returnUpdatedAtInstantiatesSameAsCreatedAt_Timestamp() {
		Timestamp testTimestamp = new Timestamp(new Date().getTime());
		assertEquals(testTimestamp.getHours(), testStylist.getUpdatedAt().getHours());
	}

  @Test 
  public void equals_returnTrueIfPropertiesAreTheSame_true() {
  	Stylist testStylistTwo = new Stylist("stylist name", "specialty", "example.jpg");
  	assertEquals(testStylist, testStylistTwo);
  }

	@Test 
	public void all_instantiatesAsEmptyListOfStylists_true() {
		assertEquals(Stylist.all().size(), 0);
	}

	@Test
	public void getClients_returnsClientsForStylistInstance_List() {
		testStylist.save();
		Client testClient = new Client("test client", testStylist.getId());
		testClient.save();
		assertEquals(testStylist.getClients().get(0).getName(), "test client");
	}

	@Test 
	public void save_savedInstanceOfStylist_true() {
		testStylist.save();
		assertTrue(Stylist.all().get(0).equals(testStylist));
	}

	@Test
	public void findById_returnInstanceOfStylistById_Stylist() {
		testStylist.save();
		assertTrue(Stylist.findById(testStylist.getId()).equals(testStylist));
	}

	@Test 
	public void findByName_returnsInstanceOfStylistByName_Stylist() {
		testStylist.save();
		assertTrue(Stylist.findByName(testStylist.getName()).equals(testStylist));
	}

	@Test 
	public void update_updatesInstanceOfStylistName_true() {
		testStylist.save();
		Timestamp testTimestamp = testStylist.getUpdatedAt();
		testStylist.update("new name", "", "");
		Stylist updatedStylist =  Stylist.findById(testStylist.getId());
		assertEquals(updatedStylist.getName(), "new name");
		assertEquals(updatedStylist.getImage(), "example.jpg");
		assertTrue(updatedStylist.getUpdatedAt().after(testTimestamp));
	}

	@Test 
	public void update_updatesImgAndSpecialty_String() {
		testStylist.save();	
		testStylist.update("", "other", "other.jpeg");
		Stylist updatedStylist =  Stylist.findById(testStylist.getId());
		assertEquals(updatedStylist.getSpecialty(), "other");
		assertEquals(updatedStylist.getImage(), "other.jpeg");
		assertEquals(updatedStylist.getName(), "stylist name");
	}

	@Test 
	public void remove_deletesInstanceOfStylist_true() {
		testStylist.save();
		Client testClient = new Client("test client", testStylist.getId());
		testClient.save();
		Visit testVisit = new Visit(testStylist.getId(), testClient.getId(), "hawkmo", "add a review");
		String inputTime = "2016-05-10 06:45:00:00";
		testVisit.getDateFromString(inputTime);
		testVisit.schedule();
		testStylist.remove();
		assertEquals(Stylist.all().size(), 0);
		assertEquals(testStylist.getClients().size(), 0);
		assertEquals(testStylist.listVisitSchedule().size(), 0);
	}

	@Test 
	public void listVisitSchedule_returnAllVisitsForStylist_List() {
		Stylist testStylist = new Stylist("test stylist", "specialty", "example.url");
		testStylist.save();
		Client newTest =  new Client("test name", testStylist.getId());
		Visit testVisit = new Visit(testStylist.getId(), newTest.getId(), "mohawk", "add a review");
		String inputTime = "2016-05-10 06:45:00:00";
		testVisit.getDateFromString(inputTime);
		testVisit.schedule();
		Timestamp testTimestamp = new Timestamp(116, 04, 10, 06, 45, 00, 00);
		assertEquals(testStylist.listVisitSchedule().get(0).getVisitDate(), testTimestamp);
	}

}