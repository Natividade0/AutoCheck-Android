# Roteiro de testes Android — Executar após a primeira compilação

[ ] O aplicativo abre sem internet e mostra 48 situações e 16 categorias.
[ ] A navegação inferior abre Início, Diagnóstico, Veículos e Histórico.
[ ] A busca encontra 'óleo' mesmo ao digitar 'oleo'.
[ ] A busca encontra sinônimos como 'carro fervendo'.
[ ] Ao filtrar 'Freios', aparecem apenas sintomas de freios.
[ ] Cadastro com ano não numérico ou sem marca não é aceito.
[ ] Cadastro de Fiat Siena é salvo após fechar/reabrir o aplicativo.
[ ] Veículo selecionado permanece após reiniciar.
[ ] Excluir veículo não remove relatórios anteriores.
[ ] Perguntas seguem exatamente as opções escolhidas, sem chat de IA.
[ ] O botão de voltar restaura a pergunta anterior.
[ ] Rotacionar o aparelho restaura a consulta em andamento.
[ ] Luz de óleo com motor ligado apresenta resultado CRÍTICO mesmo com respostas sem outros sinais.
[ ] Freio com pedal indo ao fundo apresenta resultado CRÍTICO.
[ ] Ruído de freios + 'Freio demora a responder' apresenta CRÍTICO.
[ ] Resposta 'Não tenho certeza' informa que o risco não foi descartado.
[ ] Resultado contém hipóteses sem atribuir probabilidades ou indicar troca certa de peça.
[ ] 'Salvar no histórico' salva o relatório e ele permanece depois de reiniciar o aplicativo.
[ ] 'Compartilhar relatório' abre somente seletor explícito de aplicativos.
[ ] Copiar relatório para área de transferência funciona.
[ ] Excluir relatório pede confirmação.
[ ] O APK não solicita permissões de internet, câmera, microfone, contatos, localização ou arquivos.
[ ] Fonte, botões e cores permanecem legíveis no dispositivo.

## Cenários adversos
[ ] 48 fluxos concluídos pelo menos uma vez.
[ ] Botão Voltar usado até a pergunta inicial, sem erro.
[ ] Busca sem resultados não trava.
[ ] 10 consultas salvas e excluídas sem falha.
[ ] Marca e modelo com acentos e espaços são salvos corretamente.

Estes testes ainda **não foram executados em dispositivo real**. Os testes Python verificam somente a estrutura do catálogo.
