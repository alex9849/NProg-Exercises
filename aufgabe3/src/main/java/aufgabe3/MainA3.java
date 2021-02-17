package aufgabe3;

import util.Tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainA3
{
  public static void main(String[] args) throws InterruptedException
  {
    final AtomicBoolean isRunning = new AtomicBoolean(true);
    final Person person = new NonThreadSafePerson("aaaaa", "aaaaa");
    // final Person person = new ThreadSafePerson_WriteReadLock("aaaaa", "aaaaa");
    // final Person person = new ThreadSafePerson_OptimisticLock("aaaaa", "aaaaa");
    
    // Verändert die Attribute
    Runnable writer = () -> {
      while( isRunning.get() )
      {
        String rdStr = Tools.getRandomString(5);
        person.setName(rdStr, rdStr);
        Tools.randomSleep(10, 50, TimeUnit.MILLISECONDS);
      }
    };
    
    // Teste, ob Instanz konsistent ist
    Runnable reader = () -> {
      while( isRunning.get() )
      {
        String str = person.toString();
        String[] names = str.split("\\s+");
        if( names[0].equals(names[1]) == false )
        {
          System.err.println("Inconsistent data found " + names[0] + " != " + names[1]);
        }
      }
    };
    
    System.out.println("Start test");
    
    ExecutorService executor = Executors.newFixedThreadPool(2);
    executor.execute(writer);
    executor.execute(reader);
    
    TimeUnit.SECONDS.sleep(5);
    
    isRunning.set(false);
    executor.shutdown();
    System.out.println("main done");
  }
}
