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

  @Test 
  public void getName_returnsClientName_String() {
  	assertEquals(testClient.getName(), "test client");
  }

  @Test 
  public void getId_returnsClientId_int() {
  	assertEquals(testClient.getId(), 0);
  }

  @Test 
  public void getCreatedAt_returnsCreatedAt_Timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testClient.getCreatedAt().getHours(), testTimestamp.getHours());
  }

  @Test 
  public void getUpdatedAt_instantiatesSameAsCreatedAt_Timestamp() {
  	Timestamp testTimestamp = new Timestamp(new Date().getTime());
  	assertEquals(testClient.getUpdatedAt().getHours(), testTimestamp.getHours());
  }

  @Test 
  public void getStylistId_returnsAssociatedStylistId_int() {
  	assertEquals(testClient.getStylistId(), 0);
  }

  @Test 
  public void equals_returnTrueIfPropertiesAreTheSame_client() {
  	Client testClientTwo = new Client("test client", 0);
  	assertEquals(testClient, testClientTwo);
  }

  @Test 
	public void all_instantiatesAsEmptyListOfClients_0() {
		assertEquals(Client.all().size(), 0);
	}

	@Test 
	public void save_savesInstanceOfClient_client() {
		testClient.save();
		assertEquals(Client.all().get(0), testClient);
	}

	@Test 
	public void findById_returnsClientSearchedFor_client() {
		testClient.save();
		assertEquals(Client.findById(testClient.getId()), testClient);
	}

	@Test 
	public void update_updatesName_String() {
		testClient.save();
		Timestamp testTimestamp = testClient.getUpdatedAt();	
		testClient.updateName("new name");
		Client updatedClient =  Client.findById(testClient.getId());
		assertEquals(updatedClient.getName(), "new name");
		assertTrue(updatedClient.getUpdatedAt().after(testTimestamp));
	}

	@Test 
	public void update_updatesStylist_int() {
		testClient.save();
		testClient.updateStylist(2);
		Client updatedClient = Client.findById(testClient.getId());
		assertEquals(updatedClient.getStylistId(), 2);
	}

	@Test 
	public void remove_deletesInstanceOfclient_0() {
		testClient.save();
		testClient.remove();
		assertEquals(Client.all().size(), 0);
	}
}