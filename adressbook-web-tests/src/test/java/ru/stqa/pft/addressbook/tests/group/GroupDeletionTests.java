package ru.stqa.pft.addressbook.tests.group;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getNavigationHelper().gotoGroupPage();
      app.getGroupHelper().createGroup( new GroupData("groupForDeletion", "groupForDeletion", "groupForDeletion"));
      app.getNavigationHelper().returnToGroupPage();
    }
    int before = app.getGroupHelper().getGroupCount();
    app.getGroupHelper().selectGroup(before - 1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getNavigationHelper().gotoGroupPage();
    int after = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(after, before - 1);
  }

}
