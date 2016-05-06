import java.util.Date;
import java.sql.Timestamp;

public class Stylist {
	private int id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String stylist_name;
	private String stylist_specialty;
	private String img_url;
	
	public Stylist(String stylist_name, String stylist_specialty, String img_url) {
		this.stylist_name = stylist_name;
		this.stylist_specialty = stylist_specialty;
		this.img_url = img_url;
	}

	@Override public boolean equals(Object otherStylist) {
		if(!(otherStylist instanceof Stylist)) {
			return false;
		} else {
			Stylist newStylist = (Stylist) otherStylist;
			return this.getName().equals(newStylist.getName()) &&
			this.getSpecialty().equals(newStylist.getSpecialty());
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
}