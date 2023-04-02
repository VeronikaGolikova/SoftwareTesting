package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase{

    public ContactHelper (WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNick());
        type(By.name("email"), contactData.getEmail());
    }

    public int count() {
        return wd.findElements(By.xpath("//tr[@name]")).size();
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.xpath("//input[@value='" + id + "']")).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void editSelectedContactById(int id) {
        wd.findElement(By.xpath("//a[contains(@href,'edit.php?id=" + id + "')]")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public ContactData create(ContactData contactData) {
        fillContactForm(contactData);
        submitContactCreation();
        contactCache = null;
        return contactData;
    }

    public void delete(ContactData contact) {
        wd.findElement(By.xpath("//input[@value='" + contact.getId() + "']")).click();
        deleteSelectedContact();
        submitContactDeletion();
        contactCache = null;
    }

    public void edit(ContactData contactForModification) {
        selectContactById(contactForModification.getId());
        editSelectedContactById(contactForModification.getId());
        contactCache = null;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            String lastName = row.findElement(By.xpath(".//td[2]")).getText();
            String firstName = row.findElement(By.xpath(".//td[3]")).getText();
            String allPhones = row.findElement(By.xpath(".//td[6]")).getText();
            String address = row.findElement(By.xpath(".//td[4]")).getText();
            String allEmails = row.findElement(By.xpath(".//td[5]")).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("id"));
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName)
                    .withAllPhones(allPhones).withAllEmails(allEmails);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        edit(contact);
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstName).withLastname(lastName)
                .withMobilePhone(mobile).withWorkPhone(work).withHomePhone(home)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    public void addContactInGroup(ContactData contactForIncluding, GroupData groupForIncluding) {
        selectContactById(contactForIncluding.getId());
        Select drpGroup= new Select(wd.findElement(By.name("to_group")));
        drpGroup.selectByValue(groupForIncluding.getId()+"");
        wd.findElement(By.name("add")).click();
    }

    public void deleteContactFromGroup(ContactData contactForExcluding, GroupData groupForExcluding) {
        Select drpGroup= new Select(wd.findElement(By.name("group")));
        drpGroup.selectByValue(groupForExcluding.getId()+"");
        selectContactById(contactForExcluding.getId());
        wd.findElement(By.name("remove")).click();
    }
}
