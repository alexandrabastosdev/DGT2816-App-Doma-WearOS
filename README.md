# Projeto Doma - Wear OS Acessibilidade ⌚

Este projeto foi desenvolvido como Trabalho Prático para a disciplina **DGT2816 - Interação com sensores de smartphones e wearebles**. 

## 🎯 Objetivo
A empresa fictícia "Doma" necessitava de um aplicativo Wear OS voltado para a assistência de funcionários com necessidades especiais. O objetivo principal deste app é utilizar os recursos de áudio e conectividade do smartwatch para fornecer instruções, alertas e leitura de notificações.

## 🛠️ Funcionalidades Implementadas
* **Detecção de Áudio Interno:** O app verifica via `AudioDeviceInfo.TYPE_BUILTIN_SPEAKER` se o smartwatch possui alto-falante integrado.
* **Detecção de Fone Bluetooth:** Verifica via `AudioDeviceInfo.TYPE_BLUETOOTH_A2DP` se há um dispositivo de áudio externo conectado.
* **Redirecionamento Automático (Intent):** Caso um fone não seja detectado, o aplicativo utiliza a `ACTION_BLUETOOTH_SETTINGS` para direcionar o usuário imediatamente à tela de pareamento do Wear OS, facilitando a acessibilidade.
* **Callbacks de Áudio:** Monitoramento em tempo real (`AudioDeviceCallback`) para identificar quando um fone é conectado ou desconectado.

## 💻 Tecnologias Utilizadas
* **IDE:** Android Studio
* **Linguagem:** Kotlin
* **SDK:** Wear OS API 30 a 37 (Emulador: Wear OS Small Round)
* **Permissões:** `BODY_SENSORS`, `WAKE_LOCK`, `android.hardware.type.watch`

## 📁 Documentação
A documentação completa com o passo a passo da configuração, prints de execução e detalhes da lógica implementada está disponível no arquivo PDF anexado na raiz deste repositório.