package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

    protected final static ApplicationManager app = new ApplicationManager(new Browser() {
        @Override
        public String browserName() {
            return System.getProperty("browser", Browser.FIREFOX.browserName());
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Browser) {
                return this.browserName().equals(((Browser)obj).browserName());
            } else
                return false;
        }
    });

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    public void verifyGroupListInUi() {
        if(Boolean.getBoolean("verifyUi")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g)->(new GroupData().withId(g.getId()).withName(g.getName())))
                    .collect(Collectors.toSet())));
        }
    }

    public void verifyContactListInUi() {
        if(Boolean.getBoolean("verifyUi")) {
            Contacts dbContacts = app.db().contacts();
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts, equalTo(dbContacts.stream()
                    .map((g)->(new ContactData().withId(g.getId()).withFirstname(g.getFirstname()).withLastname(g.getLastname())))
                    .collect(Collectors.toSet())));
        }
    }
}
