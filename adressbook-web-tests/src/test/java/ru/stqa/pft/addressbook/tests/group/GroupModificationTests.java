package ru.stqa.pft.addressbook.tests.group;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup( new GroupData("groupForDeletion", "groupForDeletion", "groupForDeletion"));
            app.getNavigationHelper().returnToGroupPage();
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() - 1);
        app.getGroupHelper().initGroupModification();
        GroupData fillGroup = new GroupData(before.get(before.size() - 1).getId(),"testModification", "testModification", "testModification");
        app.getGroupHelper().fillGroupForm(fillGroup);
        app.getGroupHelper().submitGroupModification();
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(fillGroup);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}
