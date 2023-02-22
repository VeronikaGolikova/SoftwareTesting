package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroopCreationTests extends TestBase {

  @Test
  public void testGroopCreation() throws Exception {
    gotoPage("groups");
    initGroupCreation();
    fillGroupForm(new GroupData("test3", "testFire", "testFire"));
    submitGroupCreation();
    gotoPage("groups");
    gotoPage("Logout");
  }

}
