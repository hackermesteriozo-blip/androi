# Android System Pro

Projeto inicial de aplicativo Android nativo em Kotlin + Jetpack Compose.

## Requisitos
- Android Studio recente
- JDK 17
- Android SDK 35 instalado
- Gradle compatível com Android Gradle Plugin 8.9.1

## Abrir e gerar o APK
1. Extraia o ZIP.
2. Abra a pasta `AndroidSystemPro` no Android Studio.
3. Aguarde o Gradle Sync e instale os componentes do SDK solicitados.
4. Conecte um aparelho Android com depuração USB ou crie um emulador.
5. Use **Run** para testar.
6. Para gerar o APK: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
7. O APK de debug ficará em `app/build/outputs/apk/debug/app-debug.apk`.

## O que já funciona
- Painel inicial com informações do aparelho.
- Fabricante, modelo, versão Android, API, build e arquitetura.
- Consulta de armazenamento disponível/total do próprio dispositivo.
- Central informativa de segurança.
- Navegação inferior e interface escura.

## Limites e próximos módulos
Este projeto é um aplicativo, não uma ROM nem um sistema operacional completo.
Gerenciamento avançado de outros apps, instalação/desinstalação, serviços em segundo plano,
terminal com privilégios e alterações de sistema exigem APIs/permissões especiais ou
privilégios de sistema. Não são simulados neste MVP.

Para evoluir para um sistema Android completo, o caminho é estudar AOSP e criar uma
build para um dispositivo específico, com bootloader desbloqueável e drivers compatíveis.
