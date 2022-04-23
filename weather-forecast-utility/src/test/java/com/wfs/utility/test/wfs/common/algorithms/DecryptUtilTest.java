package com.wfs.utility.test.wfs.common.algorithms;

import com.wfs.utility.algorithms.DecryptUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DecryptUtilTest {
    @Test
    void testEncryptDecrypt() {
        String input = "testSecretKeyEncryption";//"testEncryptedDecrypt";
        String cipherText = DecryptUtil.encrypt(input);
        System.out.println(cipherText);
        String decryptText = DecryptUtil.decrypt(cipherText);
        Assertions.assertEquals(input, decryptText);
    }
}
