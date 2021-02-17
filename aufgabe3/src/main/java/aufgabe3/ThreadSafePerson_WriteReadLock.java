package aufgabe3;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafePerson_WriteReadLock implements Person {
    private final ReentrantReadWriteLock.ReadLock rLock;
    private final ReentrantReadWriteLock.WriteLock wLock;
    private String firstname;
    private String lastname;

    ThreadSafePerson_WriteReadLock(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.rLock = lock.readLock();
        this.wLock = lock.writeLock();
    }

    @Override
    public void setName(String firstname, String lastname) {
        wLock.lock();
        try {
            this.firstname = firstname;
            this.lastname = lastname;
        } finally {
            wLock.unlock();
        }
    }

    @Override
    public String toString() {
        rLock.lock();
        try {
            return this.firstname + " " + this.lastname;
        } finally {
            rLock.unlock();
        }
    }
}
