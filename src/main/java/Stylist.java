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
			this.getSpecialty().equals(newStylist.getSpecialty());
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

	public static List<Stylist> all() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT id, created_at, updated_at, stylist_name, stylist_specialty, img_url FROM stylists";
			return con.createQuery(sql)
				.executeAndFetch(Stylist.class);
		}
	}
}