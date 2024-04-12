package pkg.skillfactoryspring.utility;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Crittografia {

    @Autowired
    BasicTextEncryptor textEncryptor;

    public String encrypt(String data){
        return textEncryptor.encrypt(data);
    }

    /*
        this method provides a decryption of the String passed in input
     */
    public String decrypt(String encriptedData){
        return textEncryptor.decrypt(encriptedData);
    }
}
