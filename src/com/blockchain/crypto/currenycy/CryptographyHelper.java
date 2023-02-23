package com.blockchain.crypto.currenycy;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {

    public static String generateHash(String data){
        StringBuilder hexaDecimalString=new StringBuilder();
        try{
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
            byte[] hash=  messageDigest.digest(data.getBytes("UTF-8"));

            for (int i = 0; i < hash.length; i++) {
                String hexaDecimalValue=Integer.toHexString(0xff & hash[i]);
                if(hexaDecimalValue.length()==1){
                    hexaDecimalString.append('0');
                }
                hexaDecimalString.append(hexaDecimalValue);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(" Something went wrong....");
        }
        return hexaDecimalString.toString();
    }

    // ECDSA-> Elliptic Curve Digital Signature Algorithm
    //
    public static byte[] sign(PrivateKey privateKey,String input){
        Signature signature;
        byte[] output=new byte[0];
        try{
            // We use bouncy Castle for Elliptic Curve
            signature=Signature.getInstance("ECDSA","BC");
            signature.initSign(privateKey);
            signature.update(input.getBytes());
            output=signature.sign();
        }catch (Exception e){
            throw new RuntimeException("Something went wrong...",e);
        }
        return output;
    }

    // check whether is the given transaction belong to the sender based on the given signature
    public static boolean verify(PublicKey publicKey,String data,byte[] signature){
        Signature ecdsaSignature=null;
        try{
            ecdsaSignature=Signature.getInstance("ECDSA","BC");
            ecdsaSignature.initVerify(publicKey);
            ecdsaSignature.update(data.getBytes());
           return ecdsaSignature.verify(signature);
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    // Generate public and private key
    public static KeyPair ellipticCurveCrypto(){
        try{
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("ECDSA","BC");
            ECGenParameterSpec ecGenParameterSpec=new ECGenParameterSpec("prime256v1");
            keyPairGenerator.initialize(ecGenParameterSpec);
            return keyPairGenerator.generateKeyPair();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
