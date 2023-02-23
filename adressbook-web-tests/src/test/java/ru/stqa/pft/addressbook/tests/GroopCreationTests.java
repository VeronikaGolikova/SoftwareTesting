package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroopCreationTests extends TestBase {

  @Test
  public void testGroopCreation() throws Exception {
    app.gotoPage("groups");
    app.initGroupCreation();
    app.fillGroupForm(new GroupData("test3", "testFire", "testFire"));
    app.submitGroupCreation();
    app.gotoPage("groups");
    app.gotoPage("Logout");
  }

}
