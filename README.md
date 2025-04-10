# PanoramaMaker - Plugin para Minecraft

Plugin para crear panoramas en Minecraft (PaperMC 1.21.1)

## Requisitos

-   Java 21
-   Servidor PaperMC 1.21.1 o superior

## Compilación

```bash
./gradlew shadowJar
```

El JAR se generará en `/out/PanoramaMaker-1.0.0.jar`

## Comandos

-   `/panoramamaker` o `/pm` - Muestra ayuda
-   `/pm make` - Inicia una sesión de panorama
-   `/pm stop` - Detiene la sesión actual
-   `/pm reload` - Recarga la configuración

## Permisos

-   `panoramamaker.make` - Permite usar /pm make
-   `panoramamaker.stop` - Permite usar /pm stop
-   `panoramamaker.reload` - Permite usar /pm reload

## Configuración

Edita `config.yml` en la carpeta del plugin para personalizar el comportamiento.
