import java.util.Date;
import java.sql.Timestamp;
import org.sql2o.*;
import java.util.List;

public class Visit {

	@Override public boolean equals(Object otherVisit) {
		if(!(otherVisit instanceof Visit)) {
			return false;
		} else {
			Visit newVisit = (Visit) otherVisit;
			return newVisit.getStylistId() == this.getStylistId() &&
			this.getClientId() == newVisit.getClientId() &&
			this.getDescription().equals(newVisit.getDescription()) &&
			this.getReview().equals(newVisit.getReview());
		}
	}


	// setters
	private int id;
	private Timestamp visit_datetime;
	private int stylist_id;
	private int client_id;
	private String style_description;
	private String style_review;
	private Timestamp created_at;
	private Timestamp updated_at;

	public Visit(int stylist_id, int client_id, String style_description, String style_review, Timestamp visit_datetime) {
		this.visit_datetime = visit_datetime;
		this.style_description = style_description;
		this.style_review = style_review;
		this.stylist_id = stylist_id;
		this.client_id = client_id;
		this.created_at = new Timestamp(new Date().getTime());
		this.updated_at = new Timestamp(new Date().getTime());
	}


	// create
	public void schedule() {
		System.out.println(this.id);
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO visits (visit_datetime, stylist_id, client_id, style_description, style_review, created_at, updated_at) VALUES (:visit_datetime, :stylist_id, :client_id, :style_description, :style_review, :created_at, :updated_at)";
			this.id = (int) con.createQuery(sql, true)
			.addParameter("visit_datetime", this.visit_datetime)
			.addParameter("stylist_id", this.stylist_id)
			.addParameter("client_id", this.client_id)
			.addParameter("style_description", this.style_description)
			.addParameter("style_review", this.style_review)
			.addParameter("created_at", this.created_at)
			.addParameter("updated_at", this.updated_at)
			.executeUpdate()
			.getKey();
			System.out.println(this.id);	
		}
	}

	//read 
	public static List<Visit> all() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT visit_datetime, style_description, style_review, stylist_id, client_id, created_at, updated_at FROM visits";
			return con.createQuery(sql)
			.executeAndFetch(Visit.class);
		}
	}

	// update


	// delete



	// getters
	public int getId() {
		return this.id;
	}

	public Timestamp getVisitDate() {
		return this.visit_datetime;
	}

	public int getStylistId() {
		return this.stylist_id;
	}

	public int getClientId() {
		return this.client_id;
	}

	public String getDescription() {
		return this.style_description;
	}

	public String getReview() {
		return this.style_review;
	}

	public Timestamp getCreatedAt() {
		return this.created_at;
	}

	public Timestamp getUpdatedAt() {
		return this.updated_at;
	}
}