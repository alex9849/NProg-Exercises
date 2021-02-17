package aufgabe3;

import java.util.concurrent.locks.StampedLock;

public class ThreadSafePerson_OptimisticLock implements Person {
  private final StampedLock lock;
  private String firstname;
  private String lastname;

  ThreadSafePerson_OptimisticLock(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.lock = new StampedLock();
  }

  @Override
  public void setName(String firstname, String lastname) {
    long stamp = lock.writeLock();
    try {
      this.firstname = firstname;
      this.lastname = lastname;
    } finally {
      lock.unlockWrite(stamp);
    }
  }

  @Override
  public String toString() {
    long stamp = lock.tryOptimisticRead();
    String tmpFn = this.firstname;
    String tmpLn = this.lastname;
    if (!lock.validate(stamp)) {
      stamp = lock.readLock();
      try {
        return this.firstname + " " + this.lastname;
      } finally {
        lock.unlockRead(stamp);
      }
    } else {
      return tmpFn + " " + tmpLn;
    }
  }
}
