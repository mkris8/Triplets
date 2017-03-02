/**
 * Created by manojkrishnamurthy on 23/02/2017.
 */

import org.junit.*;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TripletsTest {

    List<Triplets<String, Integer, Double>> triplets;

    @Before
    public void init() {
        triplets = new ArrayList<Triplets<String, Integer, Double>>();
    }

    // Tests for null params. IllegalArgumentException if a null is passed as a param.

    @Test(expected = IllegalArgumentException.class)
    public void checkIfAllNullRecord() throws Throwable {
        triplets.add(null);
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertNull(resultTriplet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfNullLabelRecord() {
        triplets.add(new Triplets<String, Integer, Double>(null, 0, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertNull(resultTriplet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfNullImageNumberRecord() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", null, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertNull(resultTriplet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfNullConfidenceRecord() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 90, null));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertNull(resultTriplet);
    }

    // Tests for null and a valid record
    @Test
    public void checkIfNullandPassportRecord() {
        triplets.add(null);
        triplets.add(new Triplets<String, Integer, Double>("passport", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 1, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.0), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfNullandIdDocumentRecord() {
        triplets.add(null);
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 1, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("iddocument", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.0), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfNullandDefaultRecord() {
        triplets.add(null);
        triplets.add(new Triplets<String, Integer, Double>("default", 1, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("default", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.0), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfNullandMiscellaneousRecord() {
        triplets.add(new Triplets<String, Integer, Double>("passport", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 1, 190.00));
        triplets.add(null);
        triplets.add(new Triplets<String, Integer, Double>("idocument", 1, 190.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(190.0), resultTriplet.getMatchConfidence());
    }

    // Test for empty labels
    @Test
    public void checkIfEmptyLabelRecord() {
        triplets.add(new Triplets<String, Integer, Double>("", 1, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.0), resultTriplet.getMatchConfidence());
    }

    // Tests to check confidence
    @Test
    public void checkIfReturnsPassportWithHighConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("passport", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 4, 190.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(4), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(190.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsFirstPassportWithCommonConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("passport", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 4, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsIDDocumentWithHighConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 1, 70.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy", 2, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 2, 80.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy", 4, 290.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("iddocument", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(2), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(80.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsIDDocumentWithCommonConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 1, 70.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy", 2, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 2, 70.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy", 4, 290.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("iddocument", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(70.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsDefaulltDocumentWithHighConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy", 2, 100.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("dummy", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(2), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(100.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsFirstDefaulltDocumentWithCommonConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy", 2, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("dummy", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.00), resultTriplet.getMatchConfidence());
    }

    // Test for various document type combinations
    @Test
    public void checkIfPassportAndIDDocumentCombinationReturnsPassport() {
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 2, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(2), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfPassportAndIDDocumentCombinationReturnsHighConfidencePassport() {
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 2, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 3, 190.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(3), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(190.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfPassportAndDefaultCombinationReturnsPassport() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 2, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(2), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.00), resultTriplet.getMatchConfidence());
    }

    // Test for combinations with confidence
    @Test
    public void checkIfPassportAndDefaultCombinationReturnsHighConfidencePassport() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 2, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("passport", 3, 190.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("passport", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(3), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(190.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfIdDocumentAndDefaultCombinationReturnsIdDocument() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 2, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("iddocument", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(2), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfIdDocumentAndDefaultCombinationReturnsHighConfidenceIdDocument() {
        triplets.add(new Triplets<String, Integer, Double>("dummy", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 2, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("iddocument", 3, 190.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("iddocument", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(3), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(190.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsMultiDefaulltDocumentWithCommonConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("dummy_1", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy_2", 2, 90.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("dummy_1", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(1), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(90.00), resultTriplet.getMatchConfidence());
    }

    @Test
    public void checkIfReturnsMultiDefaulltDocumentWithHighConfidence() {
        triplets.add(new Triplets<String, Integer, Double>("dummy_1", 1, 90.00));
        triplets.add(new Triplets<String, Integer, Double>("dummy_2", 2, 190.00));
        Triplets<String, Integer, Double> resultTriplet = Triplets.rankRecords(triplets);
        Assert.assertEquals("dummy_2", resultTriplet.getLabel());
        Assert.assertEquals(new Integer(2), resultTriplet.getImageNumber());
        Assert.assertEquals(new Double(190.00), resultTriplet.getMatchConfidence());
    }



}