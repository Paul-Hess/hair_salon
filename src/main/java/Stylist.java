import java.util.Date;
import java.sql.Timestamp;
import org.sql2o.*;
import java.util.List;

public class Stylist {

	@Override public boolean equals(Object otherStylist) {
		if(!(otherStylist instanceof Stylist)) {
			return false;
		} else {
			Stylist newStylist = (Stylist) otherStylist;
			return this.getName().equals(newStylist.getName()) &&
			this.getSpecialty().equals(newStylist.getSpecialty()) && 
			this.getId() == newStylist.getId();
		}
	}

// setters
	private int id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String stylist_name;
	private String stylist_specialty;
	private String img_url;

	
	public Stylist(String stylist_name, String stylist_specialty, String img_url) {
		this.created_at = new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());
		this.stylist_name = stylist_name;
		this.stylist_specialty = stylist_specialty;
		this.img_url = img_url;
	}

// create
	public void save() {
		String sql = "INSERT INTO stylists (created_at, updated_at, stylist_name, stylist_specialty, img_url) VALUES (:created_at, :updated_at, :stylist_name, :stylist_specialty, :img_url)";
		try(Connection con = DB.sql2o.open()) {
			this.id = (int) con.createQuery(sql, true)
				.addParameter("created_at", this.created_at)
				.addParameter("updated_at", this.updated_at)
				.addParameter("stylist_name", this.stylist_name)
				.addParameter("stylist_specialty", this.stylist_specialty)
				.addParameter("img_url", this.img_url)
				.executeUpdate()
				.getKey();
		}
	}
// read
	public static List<Stylist> all() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT id, created_at, updated_at, stylist_name, stylist_specialty, img_url FROM stylists";
			return con.createQuery(sql)
				.executeAndFetch(Stylist.class);
		}
	}

	public static Stylist findById(int id) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM stylists WHERE id=:id";
			return con.createQuery(sql)
				.addParameter("id", id)
				.executeAndFetchFirst(Stylist.class);
		}
	}

	public static Stylist findByName(String name) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM stylists WHERE stylist_name=:stylist_name";
			return con.createQuery(sql)
			.addParameter("stylist_name", name)
			.executeAndFetchFirst(Stylist.class);
		}
	}

	public List<Client> getClients() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM clients WHERE stylist_id=:id";
			return con.createQuery(sql)
			.addParameter("id", this.id)
			.executeAndFetch(Client.class);
		}
	}

	public List<Visit> listVisitSchedule() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM visits WHERE stylist_id=:id ORDER BY visit_datetime DESC";
			return con.createQuery(sql)
			.addParameter("id", this.id)
			.executeAndFetch(Visit.class);
		}
	}
// update
	public void update(String newNameValue, String newSpecialtyValue, String newImgValue) {
		if(newNameValue.length() > 0) {
			newNameValue = newNameValue;
		} else {
			newNameValue = this.getName();
		}
		if(newSpecialtyValue.length() > 0) {
			newSpecialtyValue = newSpecialtyValue;
		} else {
			newSpecialtyValue = this.getSpecialty();
		}
		if (newImgValue.length() > 0) {
			newImgValue = newImgValue;
		} else {
			newImgValue = this.getImage();
		}
		try(Connection con = DB.sql2o.open()) {
			String sql = "UPDATE stylists SET stylist_name = :stylist_name, stylist_specialty = :stylist_specialty, img_url = :img_url, updated_at = :updated_at WHERE id=:id";
			con.createQuery(sql)
				.addParameter("id", this.id)
				.addParameter("stylist_name", newNameValue)
				.addParameter("stylist_specialty", newSpecialtyValue)
				.addParameter("img_url", newImgValue)
				.addParameter("updated_at", new Timestamp(new Date().getTime()))
				.executeUpdate();
		}
	}
// delete
	public void remove() {	
		int currentId = this.id;
		try(Connection con = DB.sql2o.open()) {
			String moveClientsQuery = "UPDATE clients SET stylist_id=:newId WHERE stylist_id=:id";
			con.createQuery(moveClientsQuery)
			.addParameter("id", currentId)
			.addParameter("newId", 0)
			.executeUpdate();	
			String deleteVisitsQuery = "DELETE FROM visits WHERE stylist_id=:stylist_id";
			con.createQuery(deleteVisitsQuery)
			.addParameter("stylist_id", this.id)
			.executeUpdate();
			String deleteStylistsQuery = "DELETE FROM stylists WHERE id=:id";
			con.createQuery(deleteStylistsQuery)
			.addParameter("id", this.id)
			.executeUpdate();	
		}
	}
	// getters
	public String getName() {
		return stylist_name;
	}

	public String getSpecialty() {
		return stylist_specialty;
	}

	public String getImage() {
		return img_url;
	}

	public Timestamp getCreatedAt() {
		return created_at;
	}

	public Timestamp getUpdatedAt() {
		return updated_at;
	}

	public int getId() {
		return this.id;
	}

}