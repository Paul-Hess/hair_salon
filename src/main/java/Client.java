import java.util.Date;
import java.sql.Timestamp;
import org.sql2o.*;
import java.util.List;

public class Client {

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
}