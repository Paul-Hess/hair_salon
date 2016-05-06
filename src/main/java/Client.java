import java.util.Date;
import java.sql.Timestamp;
import org.sql2o.*;
import java.util.List;

public class Client {

	@Override public boolean equals(Object otherClient) {
		if(!(otherClient instanceof Client)) {
			return false;
		} else {
			Client newClient = (Client) otherClient;
			return this.getName().equals(newClient.getName()) &&
			this.getStylistId() == newClient.getStylistId() && 
			this.getId() == newClient.getId();
		}
	}
// setters
	private int id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String client_name;
	private int stylist_id;

	public Client(String client_name, int stylist_id) {
		this.created_at = new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());
		this.client_name = client_name;
		this.stylist_id = stylist_id;
	}

// create
public void save() {
	try(Connection con = DB.sql2o.open()) {
		String sql = "INSERT INTO clients (created_at, updated_at, client_name, stylist_id) VALUES (:created_at, :updated_at, :client_name, :stylist_id)";
		this.id = (int) con.createQuery(sql, true)
			.addParameter("created_at", this.created_at)
			.addParameter("updated_at", this.updated_at)
			.addParameter("client_name", this.client_name)
			.addParameter("stylist_id", this.stylist_id)
			.executeUpdate()
			.getKey();	
	}
}
// read
public static List<Client> all() {
	try(Connection con = DB.sql2o.open()) {
		String sql = "SELECT id, created_at, updated_at, client_name, stylist_id FROM clients";
		return con.createQuery(sql)
		.executeAndFetch(Client.class);
	}	
}
public static Client findById(int id) {
	try(Connection con = DB.sql2o.open()) {
		String sql = "SELECT * FROM clients WHERE id=:id";
		return con.createQuery(sql)
		.addParameter("id", id)
		.executeAndFetchFirst(Client.class);
	}
}

public Stylist getStylist() {
	try(Connection con = DB.sql2o.open()) {
		String sql = "SELECT * FROM stylists WHERE id=:stylist_id";
		return con.createQuery(sql)
		.addParameter("stylist_id", this.stylist_id)
		.executeAndFetchFirst(Stylist.class);
	}
}

public List<Visit> getAppointments() {
	try(Connection con = DB.sql2o.open()) {
		String sql = "SELECT * FROM visits WHERE client_id=:id";
		return con.createQuery(sql)
		.addParameter("id", this.id)
		.executeAndFetch(Visit.class);
	}
}
// update
public void updateName(String newNameValue) {
	try(Connection con = DB.sql2o.open()) {
		String sql = "UPDATE clients SET client_name = :client_name, updated_at = :updated_at WHERE id=:id";
		con.createQuery(sql)
			.addParameter("id", this.id)
			.addParameter("updated_at", new Timestamp(new Date().getTime()))
			.addParameter("client_name", newNameValue)
			.executeUpdate();
	}
}

public void updateStylist(int newStylistId) {
	try(Connection con = DB.sql2o.open()) {
		String sql = "UPDATE clients SET stylist_id = :stylist_id, updated_at = :updated_at WHERE id=:id";
		con.createQuery(sql)
		.addParameter("id", this.id)
		.addParameter("updated_at", new Timestamp(new Date().getTime()))
		.addParameter("stylist_id", newStylistId)
		.executeUpdate();
	}
}

// delete
public void remove() {
	String deleteVisitsQuery = "DELETE FROM visits WHERE client_id=:id";
	try(Connection con = DB.sql2o.open()) {
		con.createQuery(deleteVisitsQuery)
		.addParameter("id", this.id)
		.executeUpdate();
	}
	String deleteClientQuery = "DELETE FROM clients WHERE id=:id";
	try(Connection con = DB.sql2o.open()) {
		con.createQuery(deleteClientQuery)
		.addParameter("id", this.id)
		.executeUpdate();
	}
}

// getters
public String getName() {
	return this.client_name;
}

public int getId() {
	return this.id;
}

public Timestamp getCreatedAt() {
	return this.created_at;
}

public Timestamp getUpdatedAt() {
	return this.updated_at;
}

public int getStylistId() {
	return this.stylist_id;
}

}