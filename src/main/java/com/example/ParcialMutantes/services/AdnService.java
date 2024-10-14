package com.example.ParcialMutantes.services;

import com.example.ParcialMutantes.repositories.AdnRepository;
import com.example.ParcialMutantes.entities.Adn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdnService {

    private final AdnRepository adnRepository;  // Inyecta una dependencia del repositorio para interactuar con la base de datos.
    private static final int SEQUENCE_LENGTH = 4;  // Constante que define la longitud mínima de una secuencia de ADN repetida para identificar un mutante.

    @Autowired  // Anotación para inyectar automáticamente el `AdnRepository` cuando se crea una instancia de `AdnService`.
    public AdnService(AdnRepository adnRepository) {
        this.adnRepository = adnRepository;  // Asigna el repositorio inyectado al campo `adnRepository`.
    }

    // Método principal para verificar si un ADN es mutante o no.
    public static boolean isMutant(String[] adn) {
        int n = adn.length;  // Almacena el tamaño de la matriz de ADN.
        int sequenceCount = 0;  // Inicializa un contador para el número de secuencias de 4 letras consecutivas.

        // Verifica las secuencias horizontales (filas).
        sequenceCount += checkRows(adn, n);
        if (sequenceCount > 1) return true;  // Si ya se han encontrado más de una secuencia, retorna que es mutante.

        // Verifica las secuencias verticales (columnas).
        sequenceCount += checkColumns(adn, n);
        if (sequenceCount > 1) return true;

        // Verifica las secuencias diagonales.
        sequenceCount += checkDiagonals(adn, n);
        if (sequenceCount > 1) return true;

            // Retorna `false` si no se han encontrado más de una secuencia.
        else return false;
    }

    // Verifica si hay secuencias consecutivas en las filas.
    private static int checkRows(String[] adn, int n) {
        int sequenceCount = 0;  // Inicializa el contador de secuencias.

        for (int i = 0; i < n; i++) {  // Itera sobre las filas.
            int count = 1;  // Inicializa el contador de caracteres consecutivos.
            for (int j = 1; j < n; j++) {  // Itera sobre los caracteres de cada fila.
                if (adn[i].charAt(j) == adn[i].charAt(j - 1)) {  // Si el carácter actual es igual al anterior...
                    count++;  // Incrementa el contador.
                    if (count == SEQUENCE_LENGTH) {  // Si la secuencia alcanza la longitud requerida...
                        sequenceCount++;  // Incrementa el contador de secuencias encontradas.
                        if (sequenceCount > 1) return sequenceCount;  // Si se encuentra más de una secuencia, se retorna.
                    }
                } else {
                    count = 1;  // Reinicia el contador si los caracteres no son iguales.
                }
            }
        }
        return sequenceCount;  // Retorna el número de secuencias encontradas.
    }

    // Verifica si hay secuencias consecutivas en las columnas.
    private static int checkColumns(String[] adn, int n) {
        int sequenceCount = 0;

        for (int j = 0; j < n; j++) {  // Itera sobre las columnas.
            int count = 1;
            for (int i = 1; i < n; i++) {  // Itera sobre los caracteres de cada columna.
                if (adn[i].charAt(j) == adn[i - 1].charAt(j)) {  // Si el carácter actual es igual al anterior...
                    count++;
                    if (count == SEQUENCE_LENGTH) {  // Si se encuentra una secuencia de longitud 4...
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;  // Si se encuentran más de una secuencia, se retorna.
                    }
                } else {
                    count = 1;  // Reinicia el contador si los caracteres no son iguales.
                }
            }
        }
        return sequenceCount;
    }

    // Verifica si hay secuencias diagonales.
    private static int checkDiagonals(String[] adn, int n) {
        int sequenceCount = 0;

        // Verifica las diagonales de izquierda a derecha (hacia abajo).
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = 0; j <= n - SEQUENCE_LENGTH; j++) {
                if (checkDiagonal(adn, i, j, 1, 1, n)) {  // Llama a la función `checkDiagonal` para verificar una diagonal en dirección (1, 1).
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }

        // Verifica las diagonales de derecha a izquierda (hacia abajo).
        for (int i = 0; i <= n - SEQUENCE_LENGTH; i++) {
            for (int j = SEQUENCE_LENGTH - 1; j < n; j++) {
                if (checkDiagonal(adn, i, j, 1, -1, n)) {  // Verifica en dirección (1, -1).
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }
        return sequenceCount;
    }

    // Verifica una diagonal en una dirección específica.
    private static boolean checkDiagonal(String[] adn, int x, int y, int dx, int dy, int n) {
        char first = adn[x].charAt(y);  // Almacena el primer carácter de la diagonal.
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {  // Verifica los siguientes caracteres en la diagonal.
            if (x + i * dx >= n || y + i * dy >= n || y + i * dy < 0 || adn[x + i * dx].charAt(y + i * dy) != first) {
                return false;  // Retorna falso si alguno de los caracteres no coincide.
            }
        }
        return true;  // Retorna verdadero si todos los caracteres coinciden.
    }

    // Analiza una secuencia de ADN para determinar si es mutante y la guarda en la base de datos.
    public boolean analizarAdn(String[] adn) {
        String adnSequence = String.join(",", adn);  // Convierte la matriz de ADN en una cadena unida por comas.

        // Verifica si el ADN ya existe en la base de datos.
        Optional<Adn> existingAdn = adnRepository.findByAdn(adnSequence);
        if (existingAdn.isPresent()) {  // Si el ADN ya ha sido analizado, retorna el resultado almacenado.
            return existingAdn.get().isMutant();
        }

        // Determina si el ADN es mutante y lo guarda en la base de datos.
        boolean isMutant = isMutant(adn);
        Adn adnEntity = Adn.builder()  // Construye una entidad `Adn` para guardar en la base de datos.
                .adn(adnSequence)
                .isMutant(isMutant)
                .build();
        adnRepository.save(adnEntity);  // Guarda el ADN en la base de datos.

        return isMutant(adn);  // Retorna si es mutante o no.
    }
}
