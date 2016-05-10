import org.sql2o.*;
import org.fluentlenium.adapter.FluentTest;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }


  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("admin");
  }

  @Test 
  public void adminRoute() {
    goTo("http://localhost:4567/admin");
    assertThat(pageSource()).contains("add a new stylist profile here: ");
  }

  @Test 
  public void stylistFormCreate() {
    goTo("http://localhost:4567/admin");
    fill("#stylist-name").with("test stylist");
    fill("#specialty").with("test specialty");
    fill("#img-url").with("local.jpg");
    submit("#stylist-create");
    assertThat(pageSource()).contains("test stylist");
  }

  @Test 
  public void stylistIndividualRoute() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.jpeg");
    testStylist.save();
     goTo("http://localhost:4567/");
     click("h3", withText("test name"));
     assertThat(pageSource()).contains("test.jpeg");
  }

  @Test 
  public void addClientToStylist() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.jpeg");
    testStylist.save();
    goTo("http://localhost:4567/stylist/" + testStylist.getId());
    fill("#new-name").with("test client");
    submit("#client-create");
    assertThat(pageSource()).contains("test client");
  }

  @Test 
  public void viewClientPage() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.jpeg");
    testStylist.save();
    goTo("http://localhost:4567/stylist/" + testStylist.getId());
    fill("#new-name").with("other client");
    submit("#client-create");
    click("a", withText("go to my page: other client"));
    assertThat(pageSource()).contains("other client");
  }

  @Test 
  public void updateStylist() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.jpeg");
    testStylist.save();
    goTo("http://localhost:4567/stylist/" + testStylist.getId());
    fill("#stylist-name").with("otherstuff");
    submit("#stylist-update");
    assertThat(pageSource()).contains("otherstuff");
  }

  @Test 
  public void updateClientName() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.jpeg");
    testStylist.save();
    goTo("http://localhost:4567/stylist/" + testStylist.getId());
    fill("#new-name").with("other client");
    submit("#client-create");
    click("a", withText("go to my page: other client"));
    fill("#new-name").with("new client name");
    submit("#client-update");
    assertThat(pageSource()).contains("new client name");
  }

  @Test 
  public void updateClientStylist() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.jpeg");
    testStylist.save();
    Stylist testStylist2 = new Stylist("test name2", "specialty2", "test.jpeg");
    testStylist2.save();
    goTo("http://localhost:4567/stylist/" + testStylist.getId());
    fill("#new-name").with("other client");
    submit("#client-create");
    click("a", withText("go to my page: other client"));
    fillSelect("#new-stylist").withIndex(1);
    submit("#client-stylist");
    assertThat(pageSource()).contains("test name2");
  }

  @Test 
  public void deleteClientRecord() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.png");
    testStylist.save();
    Client testClient = new Client("client name", testStylist.getId());
    String url = String.format("http://localhost:4567/stylist/%d/client/%d", testStylist.getId(), testClient.getId());
    goTo(url);
    submit("#account-delete");
    goTo("http://localhost:4567/stylist/" + testStylist.getId());
    assertThat(pageSource()).doesNotContain("client name");
  }

  @Test 
  public void deleteStylistRecord() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.png");
    testStylist.save();
    String url = String.format("http://localhost:4567/stylist/%d", testStylist.getId());
    goTo(url);
    submit("#stylist-delete");
    assertThat(pageSource()).doesNotContain("test name");
  }

  @Test 
  public void createVisit() {
    Stylist testStylist = new Stylist("test name", "specialty", "test.png");
    testStylist.save();
    Client testClient = new Client("test client", testStylist.getId());
    testClient.save();
    String url = String.format("http://localhost:4567/stylist/%d/client/%d", testStylist.getId(), testClient.getId());
    goTo(url);
    fill("#description").with("liberty spikes");
    fill("#date-general").with("2016-05-10");
    fill("#date-hour").with("14");
    fill("#date-minutes").with("30");
    submit("#create-visit");
    assertThat(pageSource()).contains("liberty spikes");
  }

}
