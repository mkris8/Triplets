/**
 * Created by manojkrishnamurthy on 22/02/2017.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Triplets<T, U, V> {

    private final T label;
    private final U imageNumber;
    private final V matchConfidence;

    public Triplets(T label, U imageNumber, V matchConfidence) {
        this.label = label;
        this.imageNumber = imageNumber;
        this.matchConfidence = matchConfidence;
    }

    public T getLabel() {
        return label;
    }

    public U getImageNumber() {
        return imageNumber;
    }

    public V getMatchConfidence() {
        return matchConfidence;
    }

    public static Triplets rankRecords(List<Triplets<String, Integer, Double>> tripletList) {

        //The Best record definition
        //1. Passport with highest confidence will be given the first priority
        //2. If there is no label as passport , then I will get the label IDDocument with the highest score

        List<Double> confidenceList = new ArrayList<Double>();
        List<Triplets<String, Integer, Double>> passportsList = new ArrayList<Triplets<String, Integer, Double>>();
        List<Triplets<String, Integer, Double>> idDocumentsList = new ArrayList<Triplets<String, Integer, Double>>();
        List<Triplets<String, Integer, Double>> defaultsList = new ArrayList<Triplets<String, Integer, Double>>();

        Triplets resultTriplet = null;

        if (tripletList.size() > 0) {

            for (int i = 0; i < tripletList.size(); i++) {

                if ((null != tripletList.get(i))) {

                    String label = tripletList.get(i).getLabel();
                    Integer imageNumber = tripletList.get(i).getImageNumber();
                    Double matchConfidence = tripletList.get(i).getMatchConfidence();

                    if ((null != label) && (null != imageNumber) && (null != matchConfidence)) {
                        if (label.equalsIgnoreCase("passport") && !label.equalsIgnoreCase("iddocument")) {
                            passportsList.add(tripletList.get(i));

                        } else if (label.equalsIgnoreCase("iddocument") && !label.equalsIgnoreCase("passport")) {
                            idDocumentsList.add(tripletList.get(i));

                        } else if (!label.equalsIgnoreCase("passport") && !label.equalsIgnoreCase("iddocument")) {
                            defaultsList.add(tripletList.get(i));
                        }
                    }
                }

            }
        }

        if (passportsList.size() > 0) {

            double highestPassportConfidence = getHighestConfidence(confidenceList, passportsList);

            for (int i = 0; i < passportsList.size(); i++) {
                if (passportsList.get(i).getMatchConfidence() == highestPassportConfidence) {
                    return passportsList.get(i);
                } else {
                    resultTriplet = passportsList.get(i);
                }
            }

        }

        if (idDocumentsList.size() > 0) {

            double highestIdConfidence = getHighestConfidence(confidenceList, idDocumentsList);

            for (int i = 0; i < idDocumentsList.size(); i++) {
                if (idDocumentsList.get(i).getMatchConfidence() == highestIdConfidence) {
                    return idDocumentsList.get(i);
                } else {
                    resultTriplet = idDocumentsList.get(i);
                }
            }
        }

        if (defaultsList.size() > 0) {

            double highestDefaultConfidence = getHighestConfidence(confidenceList, defaultsList);

            for (int i = 0; i < defaultsList.size(); i++) {

                if (defaultsList.get(i).getMatchConfidence() == highestDefaultConfidence) {
                    return defaultsList.get(i);

                } else {
                    resultTriplet = defaultsList.get(i);
                }
            }
        }

        validateResult(resultTriplet);

        return resultTriplet;
    }

    private static void validateResult(Triplets resultTriplet) {
        if (null == resultTriplet) {
            throw new IllegalArgumentException("A null parameter has been passed as input.");
        }
    }

    private static double getHighestConfidence(List<Double> confidenceList, List<Triplets<String, Integer, Double>> returnList) {

        for (int i = 0; i < returnList.size(); i++) {
            if (null != returnList.get(i)) {
                confidenceList.add(returnList.get(i).getMatchConfidence());
            }
        }

        return Collections.max(confidenceList);
    }

}