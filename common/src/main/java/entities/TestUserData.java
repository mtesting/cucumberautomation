package entities;

import com.google.gson.Gson;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Calendar;
import java.util.Date;

public class TestUserData {

    private LicenseCode licenseCode;
    private String userName;
    private String eMailAddress;
    private String password;
    private Title title;
    private String firstName;
    private String lastName;
    private String houseNumber;
    private String street;
    private String telephoneNumber;
    private String city;
    private String zipCode;
    private String country;
    private Date birthDate;

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getLicenseCode() {
        return licenseCode.name();
    }

    public void setLicenseCode(LicenseCode licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMailAddress() {
        return eMailAddress;
    }

    public void seteMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title.name();
    }

    public void setTitle(Title title) {
        this.title = title;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDateDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getBirthDateMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        return String.valueOf(calendar.get(Calendar.MONTH));
    }

    public String getBirthDateYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestUserData that = (TestUserData) o;
        return new EqualsBuilder()
                .append(getUserName(), that.getUserName())
                .append(geteMailAddress(), that.geteMailAddress())
                .append(getFirstName(), that.getFirstName())
                .append(getLastName(), that.getLastName())
                .append(getHouseNumber(), that.getHouseNumber())
                .append(getStreet(), that.getStreet())
                .append(getCity(), that.getCity())
                .append(getZipCode(), that.getZipCode())
                .append(getCountry(), that.getCountry())
                .append(getBirthDate(), that.getBirthDate())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUserName())
                .append(geteMailAddress())
                .append(getPassword())
                .append(getFirstName())
                .append(getLastName())
                .append(getHouseNumber())
                .append(getStreet())
                .append(getCity())
                .append(getZipCode())
                .append(getCountry())
                .append(getBirthDate())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    //@SuppressWarnings("unused")
    public enum Title {
        Mr,
        Mrs,
        Ms
    }

    public enum LicenseCode {
        MT,
        SH
    }
}
