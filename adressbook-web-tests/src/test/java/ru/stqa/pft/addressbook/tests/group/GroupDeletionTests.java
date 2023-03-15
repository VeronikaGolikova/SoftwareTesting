package ru.stqa.pft.addressbook.tests.group;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() ==0) {
      app.group().create( new GroupData().withName("groupForDeletion").withHeader("groupForDeletion").withFooter("groupForDeletion"));
      app.goTo().returnToGroupPage();
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Groups before = app.group().all();
    GroupData groupForDeletion = before.iterator().next();
    app.group().delete(groupForDeletion);
    app.goTo().groupPage();
    assertThat(app.group().count(), equalTo(before.size() - 1));
    Groups after = app.group().all();
    assertThat(after,equalTo(before.without(groupForDeletion)));
  }
}
