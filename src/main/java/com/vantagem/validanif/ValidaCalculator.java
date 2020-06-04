package com.vantagem.validanif;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * APP Responsavel pela validação do NIF
 *
 * 9 9 9 9 9 9 9 9 0
 * 9 8 7 6 5 4 3 2 1
 * ================= 81 72 ... 18 1 SOMA = 81 + 71 + ... + 18 + 1 = 396 RESTO
 * 396 / 11 = 0 (é válido)
 *
 * @author César Freire
 * @serial 20170926
 */
public class ValidaCalculator {

    private static final Logger LOG = Logger.getLogger(ValidaCalculator.class.getName());

    private static final int MAX = 9;

    private ValidaCalculator() {}

    public static boolean isValid(final String oNif) {
        final String nif = oNif.trim();
        LOG.info("Start validating: " + oNif);
        if (nif.length() != MAX) {
            LOG.log(Level.WARNING, "!= {0} digitos", MAX);
            return false;
        }
        int soma = 0;
        int numero = 0;
        for (int i = 0; i < MAX; i++) {
            char digito = nif.charAt(i); // carater da string
            numero = Character.digit(digito, 10); // cvt numero
            if (numero < 0) {
                LOG.log(Level.WARNING, "nif contêm carateres não numericos");
                return false;
            }
            int mul = numero * (MAX - i);
            soma += mul;
            LOG.log(Level.FINE, "nif:{0}\tnumero:{1}\tmul:{2}\tsoma:{3}",
                    new Object[]{nif, numero, mul, soma});
        }
        boolean resultado = (soma % 11) == 0;
        LOG.log(Level.FINE, "Resultado:{0}", resultado);
        if (!resultado && numero == 0) {
            resultado = ((soma + 10) % 11) == 0;
        }
        return resultado;
    }

    public static void main(String[] args) {
        LOG.setLevel(Level.FINE);
        System.out.println(ValidaCalculator.isValid("999999990"));
    }
}