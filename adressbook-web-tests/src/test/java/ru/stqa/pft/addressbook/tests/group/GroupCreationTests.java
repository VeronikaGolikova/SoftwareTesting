package ru.stqa.pft.addressbook.tests.group;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroopCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test1", "test1", "test1");
    app.getGroupHelper().createGroup(group);
    app.getNavigationHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() +1);

    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

}
