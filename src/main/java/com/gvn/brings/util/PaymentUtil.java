package com.gvn.brings.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

public class PaymentUtil {

    private static final String paymentKey = "LqO4WCOX";

    private static final String paymentSalt = "OTR3rRkEqU";

    private static final String sUrl = "http://brings.app/brings-dev/rest/payment-response";

    private static final String fUrl = "http://brings.app/brings-dev/rest/payment-response";

    public static HashMap<String,String> populatePaymentDetail(HashMap<String,String> inputDetails){
    	HashMap<String,String> response=new HashMap<>();
    	String hashString = "";
        Random rand = new Random();
        String randomId = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
        String txnId = "Dev" + hashCal("SHA-256", randomId).substring(0, 12);
        inputDetails.put("payTxId", txnId);
        
        String hash = "";
        //String otherPostParamSeq = "phone|surl|furl|lastname|curl|address1|address2|city|state|country|zipcode|pg";
        String hashSequence = "key|txnid|amount|orderId|firstname|email|||||||||||";
        hashString = hashSequence.concat(paymentSalt);
        hashString = hashString.replace("key", paymentKey);
        hashString = hashString.replace("txnid", txnId);
        hashString = hashString.replace("amount", inputDetails.get("amount"));
        hashString = hashString.replace("orderId", inputDetails.get("orderId"));
        hashString = hashString.replace("firstname", inputDetails.get("firstname"));
        hashString = hashString.replace("email", inputDetails.get("email"));

        hash = hashCal("SHA-512", hashString);
        inputDetails.put("hash",hash);
        inputDetails.put("fUrl",fUrl);
        inputDetails.put("sUrl",sUrl);
        inputDetails.put("key", paymentKey);
        
        return inputDetails;
    }

    public static String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

}