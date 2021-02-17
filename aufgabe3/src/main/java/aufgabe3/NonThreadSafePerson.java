package aufgabe3;

public class NonThreadSafePerson implements Person
{
  private String firstname;
  private String lastname;
  
  NonThreadSafePerson(String firstname, String lastname)
  {
    this.firstname = firstname;
    this.lastname = lastname;
  }
  
  @Override
  public void setName(String firstname, String lastname)
  {
      this.firstname = firstname;
      this.lastname = lastname;
  }

  @Override
  public String toString()
  {
      String str = this.firstname + " " + this.lastname;
      return str;
  }
}
