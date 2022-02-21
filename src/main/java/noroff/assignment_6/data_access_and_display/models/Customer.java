package noroff.assignment_6.data_access_and_display.models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.PrivateKey;

@Entity
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int customerId;
private String FirstName;
private String LastName;
private String Company;
private String Address;
private String City;
private String State;
private String Country;
private int PostalCode;
private int Phone;
private int Fax;
private String Email;
}
