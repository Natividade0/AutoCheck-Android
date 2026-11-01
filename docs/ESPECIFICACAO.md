# AutoCheck — Especificação funcional da versão 1

## Público
Motoristas que desejam organizar os sintomas do veículo antes de procurar inspeção. Aplicativo para leigos em português brasileiro.

## Escopo
- Android API 26+.
- Uso offline, sem autenticação e sem transferência obrigatória de dados.
- Motor baseado em regras, não IA generativa.
- Um sintoma por consulta; resposta por toque e sequência curta de três perguntas.
- Hipóteses não probabilísticas, sem preços e sem indicação de substituição direta de peças.
- Histórico e veículos em SQLite no aparelho.

## Fluxos
1. Tela inicial: veículo ativo e sintomas frequentes.
2. Diagnóstico: busca textual e filtro de categoria.
3. Perguntas: escolhas pré-cadastradas de contexto, sinais associados e sinais críticos.
4. Resultado: risco, hipóteses, orientação, sinais de perigo e respostas.
5. Salvar ou compartilhar: ação deliberada do usuário.
6. Histórico: consultar, copiar, compartilhar, excluir.

## Dados
`catalog.json`: `schemaVersion`, `catalogVersion`, `editorialStatus`, `categories`, `cases`.

Cada caso:
- `id` e `category` para referência.
- `title`, `aliases` e `description` para busca.
- `severity` (`routine`, `attention`, `critical`).
- `causes`: hipóteses sem ordem probabilística.
- `advice`: orientações conservadoras, sem procedimentos arriscados.
- `dangerSigns`: alertas específicos do tema.
- `questions`: lista de nós `id`, `prompt`, `help`, `options`.
- Cada opção: `label`, `next`, `note`, `critical` opcional.
- `reviewStatus`: fase editorial preliminar.

O motor usa `next` para navegar; a versão 1 tem 3 nós por caso. Resposta crítica ou gravidade-base crítica produz um alerta. Quando sinais críticos não podem ser descartados, o resultado reforça a necessidade de inspeção.

## Regras de segurança
- Se a categoria envolver perda de frenagem, lubrificação crítica ou superaquecimento, não orientar condução de teste.
- Não apresentar porcentagens inventadas ou diagnósticos definitivos.
- Não orientar desmontagem de airbag, sistema pressurizado, eletricidade ou componentes perigosos.
- Não tratar ausência de evidência como confirmação de ausência de risco.

## Objetivos de qualidade antes de publicar
- Cada caso ter revisão por profissional qualificado.
- Remover caminhos incompatíveis com a especificação do veículo.
- Criar trilha de fontes e versões por caso.
- Ensaiar cenários de emergência e revisar textos legais.
- Testar acessibilidade, rotação, memória e desempenho em Android real.
