package aufgabe3;

// Die Klasse muss noch entsprechend angepasst werden
public class ThreadSafePerson_WriteReadLock implements Person
{
  private String firstname;
  private String lastname;

  ThreadSafePerson_WriteReadLock(String firstname, String lastname)
  {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  @Override
  public void setName(String firstname, String lastname)
  {
    // to do
    this.firstname = firstname;
    this.lastname = lastname;
  }

  @Override
  public String toString()
  {
    // to do
    String str = this.firstname + " " + this.lastname;
    return str;
  }
}
