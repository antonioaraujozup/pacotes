package br.com.zup.edu.pacotes.api.dados;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Embeddable
public class CPF {

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false, unique = true, length = 64)
    private String hash;

    public CPF(String numero) {
        this.numero = this.anonymize(numero);
        this.hash = this.hash(numero);
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public CPF() {
    }

    @Override
    public String toString() {
        return this.numero;
    }

    private String anonymize(String cpf) {
        String regex = "([0-9]{3})\\.([0-9]{3})\\.([0-9]{3})\\-([0-9]{2})";
        String masked = cpf.replaceAll(regex, "$1.***.***-$4");
        return masked;
    }

    private String hash(String cpf) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            byte[] hash = digest.digest(cpf.getBytes(StandardCharsets.UTF_8));
            return toHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalStateException("Erro ao gerar hash de um CPF: " + cpf);
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }

    public String getHash() {
        return hash;
    }
}
