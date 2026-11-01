# Privacidade — AutoCheck v1 Beta

O AutoCheck v1 Beta é um aplicativo experimental com armazenamento local. Não exige conta e não contém, no projeto-fonte, cliente HTTP nem permissão de internet. Não integra anúncios, analytics, servidores de terceiros ou serviços de IA.

## Informações armazenadas
- Dados que o usuário informa sobre o veículo: marca, modelo, ano, motorização e quilometragem.
- Consultas e relatórios que o usuário decide salvar.
- Preferência do veículo atualmente selecionado.

Os registros permanecem no armazenamento privado do aplicativo no dispositivo Android. O usuário pode excluir veículos e consultas individualmente e desinstalar o aplicativo. O manifesto desta versão desabilita o backup automático do aplicativo (`allowBackup=false`).

## Compartilhamento
A função Compartilhar utiliza o seletor padrão do Android e somente transmite conteúdo depois de ação do usuário. Ao escolher um aplicativo de terceiros, a política de privacidade daquele aplicativo se aplica. A função Copiar disponibiliza o relatório à área de transferência do Android.

## Limitações
Este documento descreve apenas o comportamento do código entregue. Qualquer funcionalidade futura de nuvem, anúncios, login, pagamentos, câmera ou telemetria exigirá nova análise e atualização da política de privacidade.
