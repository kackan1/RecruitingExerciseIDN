package org.example.Person;

public class Person {
  String personId;
  String firstName;
  String lastName;
  String mobile;
  String email;
  String pesel;

  public enum PersonField{
    PERSON_ID("PersonId"),
    FIRST_NAME("FirstName"),
    LAST_NAME("LastName"),
    MOBILE("Mobile"),
    EMAIL("Email"),
    PESEL("PESEL");

    private String fieldName;

    PersonField(String xmlName) {
      this.fieldName = xmlName;
    }

    public String get(){
      return fieldName;
    }
  }

  public Person(String personId, String firstName, String lastName, String mobile, String email, String pesel) {
    this.personId = personId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.mobile = mobile;
    this.email = email;
    this.pesel = pesel;
  }

  public Person() {
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    this.pesel = pesel;
  }

  @Override
  public String toString() {
    return "Person {" +
        "PersonId='" + personId + '\'' +
        ", FirstName='" + firstName + '\'' +
        ", LastName='" + lastName + '\'' +
        ", Mobile='" + mobile + '\'' +
        ", Email='" + email + '\'' +
        ", PESEL='" + pesel + '\'' +
        '}';
  }
}

