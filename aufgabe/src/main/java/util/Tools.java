package aufgabe.util;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Tools
{
  private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

  public static String getRandomString(int len)
  {
    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < len; i++)
    {
      int idx = ThreadLocalRandom.current().nextInt(ALPHABET.length);
      strBuilder.append(ALPHABET[idx]);
    }

    return strBuilder.toString();
  }

  public static boolean isPalindrom(String str)
  {
    int len = str.length();
    for (int i = 0; i < len / 2; i++)
    {
      if (str.charAt(i) != str.charAt(len - 1 - i))
        return false;
    }

    return true;
  }

  public static boolean isPowerOfTwo(int i)
  {
    return (i & (i - 1)) == 0;
  }

  public static int[] gerRandomIntArray(int length, int origin, int bound)
  {
    int[] array = new int[length];
    Arrays.setAll(array, i -> ThreadLocalRandom.current().nextInt(origin, bound));

    return array;
  }

  public static void sleep(int timeout, TimeUnit unit)
  {
    try
    {
      unit.sleep(timeout);
    }
    catch (InterruptedException exce)
    {
      exce.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  public static void randomSleep(int minTimeout, int maxTimeout, TimeUnit unit)
  {
    try
    {
      int randTimeout = ThreadLocalRandom.current().nextInt(maxTimeout - minTimeout);
      unit.sleep(minTimeout + randTimeout);
    }
    catch (InterruptedException exce)
    {
      exce.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

}
