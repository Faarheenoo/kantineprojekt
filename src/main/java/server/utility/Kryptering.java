package server.utility;

public class Kryptering {

    /*Our incryption method "XOR", that contains incrypt and decrypt*/

    public static String encryptdecrypt(String toBeEncryptedDecrypted) {

        //Vi vælger selv værdierne til nøglen
        char[] key = {'L', 'O', 'L'};
        //En StringBuilder er en klasse, der gør det muligt at ændre en string
        StringBuilder isEncryptedDecrypted = new StringBuilder();

        for (int i = 0; i < toBeEncryptedDecrypted.length(); i++) {
            isEncryptedDecrypted.append((char) (toBeEncryptedDecrypted.charAt(i) ^ key[i % key.length]));
        }
        return isEncryptedDecrypted.toString();
    }
}

