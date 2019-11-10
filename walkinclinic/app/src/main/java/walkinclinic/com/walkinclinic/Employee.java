package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Employee extends Person implements Serializable {

  public Employee (String id, String email, String password, String name)
  {
      super(id, email, password, name);
  }
}
