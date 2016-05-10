import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      if(Stylist.all().size() > 0) {
      	model.put("stylists", Stylist.all());
      }	
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
			Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:stylist_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	int id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(id);
    	model.put("stylist", currentStylist);
    	if(currentStylist.getClients().size() > 0) {
    		model.put("clients", currentStylist.getClients());
    	}
    	model.put("template", "templates/stylist.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:stylist_id/client/:client_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	int stylist_id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(stylist_id);
    	int client_id = Integer.parseInt(request.params("client_id"));
    	Client currentClient = Client.findById(client_id);
    	model.put("stylistList", Stylist.all());
    	model.put("stylist", currentStylist);
    	model.put("client", currentClient);
        if(currentClient.getAppointments().size() > 0) {
            model.put("visits", currentClient.getAppointments());
        }
    	model.put("template", "templates/client.vtl");

    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/admin/stylist/new", (request, response) -> {
    	String name = request.queryParams("stylist-name");
    	String specialty = request.queryParams("specialty");
    	String img = request.queryParams("img-url");
    	Stylist newStylist = new Stylist(name, specialty, img);
    	newStylist.save();
    	response.redirect("/");
        return null;
    });

    post("/stylist/:stylist_id/update", (request, response) -> {
    	int id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(id);
    	String name = request.queryParams("stylist-name");
    	String specialty = request.queryParams("specialty");
    	String img = request.queryParams("img-url");
    	currentStylist.update(name, specialty, img);
    	response.redirect("/");
        return null;
    });

    post("/stylist/:stylist_id/client/new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	int id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(id);
    	String clientName = request.queryParams("new-name");
    	Client newClient = new Client(clientName, id);
    	newClient.save();
    	model.put("stylist", currentStylist);
    	model.put("clients", currentStylist.getClients());
    	model.put("template", "templates/stylist.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


     post("/stylist/:stylist_id/client/:client_id/updatename", (request, response) -> {
    	int stylist_id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(stylist_id);
    	int client_id = Integer.parseInt(request.params("client_id"));
		Client currentClient = Client.findById(client_id);
		String newClientName = request.queryParams("new-name");
		currentClient.updateName(newClientName);

		response.redirect(String.format("/stylist/%s/client/%s", stylist_id, client_id));
        return null;
    });

     post("/stylist/:stylist_id/client/:client_id/updatestylist", (request, response) -> {
    	int stylist_id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(stylist_id);
    	int client_id = Integer.parseInt(request.params("client_id"));
		Client currentClient = Client.findById(client_id);
		int newStylistId = Integer.parseInt(request.queryParams("new-stylist"));
		currentClient.updateStylist(newStylistId);
		response.redirect("/");
    	return null;
    });

     post("/stylist/:stylist_id/client/:client_id/delete", (request, response) -> {
        int client_id = Integer.parseInt(request.params("client_id"));
        Client currentClient = Client.findById(client_id);
        currentClient.remove();
        response.redirect("/");
        return null;
    });

    post("/stylist/:stylist_id/delete", (request, response) -> {
        int stylist_id = Integer.parseInt(request.params("stylist_id"));
        Stylist currentStylist = Stylist.findById(stylist_id);
        currentStylist.remove();
        response.redirect("/");
        return null;
    });

    post("/stylist/:stylist_id/client/:client_id/visit_new", (request, response) -> {
        int stylist_id = Integer.parseInt(request.params("stylist_id"));
        Stylist currentStylist = Stylist.findById(stylist_id);
        int client_id = Integer.parseInt(request.params("client_id"));
        Client currentClient = Client.findById(client_id); 
        String styleDescription = request.queryParams("description");
        String review = "you can update this record after your visit and a your review will appear here.";
        String date = request.queryParams("date-general");
        String hour = request.queryParams("date-hour");
        String minutes = request.queryParams("date-minutes");
        int zeroSeconds = 0;
        String dateInput = String.format("%s %s:%s:%d%d:%d%d", date, hour, minutes, zeroSeconds, zeroSeconds, zeroSeconds, zeroSeconds);
        Visit newVisit = new Visit(stylist_id, client_id, styleDescription, review);
        newVisit.getDateFromString(dateInput);
        newVisit.schedule();
        String url = String.format("/stylist/%s/client/%s", stylist_id, client_id);
        response.redirect(url);
        return null;
    });

  }

}