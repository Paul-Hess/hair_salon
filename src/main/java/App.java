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
    	model.put("stylist", currentStylist);
    	model.put("client", currentClient);
    	model.put("template", "templates/client.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/admin/stylist/new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	String name = request.queryParams("stylist-name");
    	String specialty = request.queryParams("specialty");
    	String img = request.queryParams("img-url");
    	Stylist newStylist = new Stylist(name, specialty, img);
    	newStylist.save();
    	response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:stylist_id/update", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	int id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(id);
    	String name = request.queryParams("stylist-name");
    	String specialty = request.queryParams("specialty");
    	String img = request.queryParams("img-url");
    	currentStylist.update(name, specialty, img);
    	response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

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
    	Map<String, Object> model = new HashMap<String, Object>();
    	int stylist_id = Integer.parseInt(request.params("stylist_id"));
    	Stylist currentStylist = Stylist.findById(stylist_id);
    	int client_id = Integer.parseInt(request.params("client_id"));
			Client currentClient = Client.findById(client_id);
			String newClientName = request.queryParams("new-name");
			currentClient.updateName(newClientName);

			response.redirect(String.format("/stylist/%s/client/%s", stylist_id, client_id));
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



  }

}