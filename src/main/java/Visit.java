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
	private int stylist_id;
	private int client_id;
	private String style_description;
	private String style_review;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Timestamp visit_datetime;

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
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO visits (visit_datetime, stylist_id, client_id, style_description, style_review, created_at, updated_at) VALUES (:visit_datetime, :stylist_id, :client_id, :style_description, :style_review, :created_at, :updated_at)";
			con.createQuery(sql)
			.addParameter("visit_datetime", this.visit_datetime)
			.addParameter("stylist_id", this.stylist_id)
			.addParameter("client_id", this.client_id)
			.addParameter("style_description", this.style_description)
			.addParameter("style_review", this.style_review)
			.addParameter("created_at", this.created_at)
			.addParameter("updated_at", this.updated_at)
			.executeUpdate();
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

	public static Visit find(int stylist_id_query, int client_id_query, Timestamp visit_datetime_query) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM visits WHERE stylist_id=:stylist_id AND client_id=:client_id AND visit_datetime=:visit_datetime";
			return con.createQuery(sql)
			.addParameter("stylist_id", stylist_id_query)
			.addParameter("client_id", client_id_query)
			.addParameter("visit_datetime", visit_datetime_query)
			.executeAndFetchFirst(Visit.class);
		}
	}

	// update
	public void reSchedule(Timestamp newDatetime) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "UPDATE visits SET visit_datetime=:new_visit_datetime WHERE stylist_id=:stylist_id AND client_id=:client_id AND visit_datetime=:old_visit_datetime";
			con.createQuery(sql)
			.addParameter("new_visit_datetime", newDatetime)
			.addParameter("stylist_id", this.stylist_id)
			.addParameter("old_visit_datetime", this.visit_datetime)
			.addParameter("client_id", this.client_id)
			.executeUpdate();
		}
	}


	// delete

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