package com.csl.csl_blinddate;

import java.util.Random;

public class GenerateCertNumber {
    private int certNumLength = 4;

    public String generate() {
        Random random = new Random(System.currentTimeMillis());

        int range = (int)Math.pow(10,certNumLength);
        int trim = (int)Math.pow(10, certNumLength-1);
        int result = random.nextInt(range)+trim;

        if(result>range){
            result = result - trim;
        }

        return String.valueOf(result);
    }

    public int getCertNumLength() {
        return certNumLength;
    }

    public void setCertNumLength(int certNumLength) {
        this.certNumLength = certNumLength;
    }
}
