package edu.campuswien.smartcity.job;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well1024a;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExponentialRandomTest {

    @Test
    public void test_exponentialRandom_setMeanOneTime() {
        RandomGenerator random = new Well1024a();
        ExponentialDistribution distribution = new ExponentialDistribution(random, 15);
        int size = 100;

        double sum = 0;
        //System.out.print("[");
        for (int i=0; i<size; i++) {
            double val = distribution.sample();
            val++;
            sum += val;
            //System.out.print(Math.round(val)+",");
        }
        //System.out.print("]");
        long roundAvg = Math.round(sum / size);
        System.out.println("Avg Math3: " + roundAvg);
        Assert.assertTrue(roundAvg >= 12 && roundAvg <= 18);
    }

    @Test
    public void test_exponentialDistribution_setMeanMoreTimes() {
        int size = 100;

        double sum = 0;
        //System.out.print("[");
        for (int i=0; i<size; i++) {
            RandomGenerator random = new Well1024a();
            ExponentialDistribution distribution = new ExponentialDistribution(random, 15);
            double val = distribution.sample();
            val++;
            sum += val;
            //System.out.print(Math.round(val)+",");
        }
        //System.out.print("]");
        long roundAvg = Math.round(sum / size);
        System.out.println("Avg Math3: " + roundAvg);
        Assert.assertTrue(roundAvg >= 12 && roundAvg <= 18);
    }

    @Test
    public void test_exponentialDistribution_setDifferentMeans() {
        int size = 100;
        int mean = 15;

        double sum = 0;
        //System.out.print("[");
        for (int i=0; i<size; i++) {
            RandomGenerator random = new Well1024a();
            int nMean = i%2==0 ? mean+1 : mean-1;
            ExponentialDistribution distribution = new ExponentialDistribution(random, nMean);
            double val = distribution.sample();
            val++;
            sum += val;
            //System.out.print(Math.round(val)+",");
        }
        //System.out.print("]");
        long roundAvg = Math.round(sum / size);
        System.out.println("Avg Math3: " + roundAvg);
        Assert.assertTrue(roundAvg >= 12 && roundAvg <= 18);
    }

    /*    private static void generatorHipparchus() {
        RandomDataGenerator randomGenerator = new RandomDataGenerator(100);

        double sum = 0;
        System.out.print("[");
        for (int i=0; i<SIZE; i++) {
            double val = randomGenerator.nextExponential(MEAN);
            sum += val;
            System.out.print(Math.round(val)+",");
        }
        System.out.print("]");
        System.out.println("Avg Hipparchus: " + Math.round(sum/SIZE));
    }*/
}
