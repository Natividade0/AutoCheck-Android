# Padrão editorial e expansão da biblioteca

O conteúdo inicial é RASCUNHO, ainda não revisado por profissional mecânico. Não publicar como diagnóstico certificado.

## Como inserir novos casos

1. Abra `tools/build_catalog.py`.
2. Adicione um caso com `add(...)` informando: categoria, título, descrição, 3 contextos, 3 sinais, 3 hipóteses de investigação e orientação conservadora.
3. Marque `critical=True` para situações intrinsecamente críticas.
4. Se determinado sintoma adicional sinalizar perigo, inclua a resposta exata no mapeamento `risk_choices`.
5. Execute `python tools/build_catalog.py` e `python tools/validate_catalog.py`.
6. Faça revisão técnica da hipótese, do alerta, dos termos e do limite da orientação.
7. Compile e teste a navegação no Android.

## Critérios de revisão por caso

- Qual(is) documento(s) técnicos fundamentam as hipóteses?
- As hipóteses são compatíveis com todos os veículos em que o caso aparecerá?
- O conteúdo explica alternativas, sem sugerir certeza?
- O risco de perder frenagem, direção, lubrificação e arrefecimento está tratado adequadamente?
- Alguma resposta pode incentivar uma verificação perigosa por leigos?
- Existe indicação para procurar serviço presencial quando necessário?

## Evolução recomendada

Atualmente, os 48 casos usam três perguntas cada; as opções avançam por `next` e podem ativar alerta crítico. Para milhares de situações tecnicamente diferentes, o conteúdo deve passar a oferecer perguntas condicionais específicas (por modelo/motorização e indícios adicionais), não apenas multiplicar combinações superficiais.

## Uso de dados e direitos

Evitar copiar integralmente manuais, apostilas ou bancos de dados de terceiros. Inserir informações reescritas, referências verificadas e revisão editorial por profissional qualificado. Não apresentar dados de preços sem fonte atual.
