# :computer: REDES DE COMPUTACIÓN :computer:

# Laboratorio de redes de corrección de errores por método Polinomico y Hamming 

Se debe elaborar un menú con las opciones para detectar y corregir errores. El programa
debe solicitar la entrada de un archivo de entrada escrito en notepad (block de notas).
También debe solicitar el nombre del archivo de salida, si no se da, se asumirá el
archivo salida.txt por defecto.El archivo de entrada puede contener cualquier combinación de palabras del juego de
caracteres ASCII correspondientes a las letras minúsculas, mayúsculas, espacio en
blanco, punto(.), coma (,), punto y coma (;) y dos puntos (:). No debe contener
caracteres inválidos.

### :warning: Detección :warning:
####  :incoming_envelope: Envío :incoming_envelope:
Cuando se escoja la opción de detección de error del menú de programa se debe aplicar
el método polinomial. Debe permitir al usuario elegir el polinomio generador de CRC
mediante la captura de los coeficientes, de tal forma que dicha sucesión de coeficientes
sean la palabra clave de verificación tanto en el emisor como el receptor. El polinomio
se debe digitar como una sucesión de 1s y 0s.

El archivo de entrada se debe convertir en binarios según ASCII. Se deben juntar hasta
un máximo de 16 caracteres válidos para formar un bloque binario de hasta 128 bits
(Cada carácter corresponde a un código de 8 bits, luego 8x16 = 128 bits). Este número
de 128 bits se debe dividir módulo 2 por el polinomio generador ingresado previamente
y a partir de allí generar las **palabras de código** correspondiente por cada 128 bits. Si la
palabra de datos no llena los 128 bits, sino menos, se debe dejar así e, igualmente, llevar
a cabo la división.

Con las ***palabras de datos* se generan las *palabras de códigos* correspondientes**. En un archivo con el mismo nombre que el archivo de datos y con extensión .crc, se debe agregar en la primera línea el polinomio generador, y luego, todas las palabras de código, una debajo de otra, en este mismo archivo.
#### :running: Transmisión y Propagación :running:
Manualmente se cambian bits en cada línea del archivo de palabras de código a través
del block de notas. O simplemente no se cambian.
#### :mailbox_with_mail: Recepción :mailbox_with_mail:
Se solicita el nombre del archivo .crc, el cual tiene en su primera línea el polinomio
generador y las subsiguientes el flujo de 1 y 0’s correspondiente a las palabras de 
código. El programa debe emitir un mensaje correspondiente a la verificación de los
datos (Mensaje Correcto o Mensaje Incorrecto). Si el mensaje es correcto, debe generar
el archivo de datos (palabras de datos original) el cual tendría extensión .txt y el nombre
sería el mismo que el del archivo .crc.
### :heavy_check_mark: Corrección :heavy_check_mark:
####  :incoming_envelope: Envío :incoming_envelope:
Cuando se escoja la opción de corrección de error del menú de programa se debe aplicar
el método Hamming para corregir un error. El programa debe trabajar con la longitud
máxima de los caracteres ASCII y de acuerdo con ello, aplicar toda la teoría para
corrección de error. Generar un archivo de chequeo de error compuesto por los bits de
datos y de verificación (palabras de código). El nombre del archivo de chequeo de error
debe ser el mismo que el del archivo de datos pero con extensión .ham. Cada carácter
ASCII en binario convertido en palabra de código se debe registrar una debajo de otra en
el archivo .ham El archivo original debe quedar sin modificación en este proceso.
#### :running: Transmisión y Propagación :running:
Se edita el archivo .ham y se cambia un bit en una o más palabra de código en forma
aleatoria o simplemente no se cambia ninguna. Se utiliza el block de notas para hacer
esta operación.
####  :mailbox_with_mail: Recepción :mailbox_with_mail:
Para corregir uno o más errores de un sólo bit por carácter, se debe dar como entrada un
archivo de chequeo .ham y se correge de acuerdo con el método Hamming en el receptor
originando un archivo de salida que debe ser idéntico al archivo de entrada original.
Nota: El programa debe ser muy personalizado.
#### :eyes: Detección :eyes:
**Emisor:** Debe tomar como entrada un archivo de texto y codificarlo según lo explicado.

**Receptor:** Debe tomar como entrada un archivo de texto de otro grupo y decodificarlo para detectar errores según lo explicado.
#### :white_check_mark: Corrección :white_check_mark:
**Emisor:** Debe tomar como entrada un archivo de texto y codificarlo según lo explicado.

**Receptor:** Debe tomar como entrada un archivo de texto de otro grupo y decodificarlo para corrección según lo explicado.
