package ru.stqa.pft.sandbox;

import org.junit.Assert;
import org.junit.Test;

public class PrimeTests {

  @Test
  public void testPrimes() {
    Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
  }

  @Test
  public void testNotPrimes() {
    Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE-2));
  }

  @Test(enabled = false)
  public void testPrimesLong() {
    long n = Integer.MAX_VALUE;
    Assert.assertTrue(Primes.isPrime(n));
  }
}
