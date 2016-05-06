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

public static List<Client> all() {
	try(Connection con = DB.sql2o.open()) {
		String sql = "SELECT id, created_at, updated_at, client_name, stylist_id FROM clients";
		return con.createQuery(sql)
		.executeAndFetch(Client.class);
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