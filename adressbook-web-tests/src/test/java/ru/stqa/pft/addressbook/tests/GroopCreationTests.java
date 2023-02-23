package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroopCreationTests extends TestBase {

  @Test
  public void testGroopCreation() throws Exception {
    app.getNavigationHelper().gotoPage("groups");
    app.getGroupHelper().initGroupCreation();
    app.getGroupHelper().fillGroupForm(new GroupData("test3", "testFire", "testFire"));
    app.getGroupHelper().submitGroupCreation();
    app.getNavigationHelper().gotoPage("groups");
    app.getNavigationHelper().gotoPage("Logout");
  }

}
