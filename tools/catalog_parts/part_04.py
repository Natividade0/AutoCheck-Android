add("Direção","Volante vibrando","Vibrações podem vir de rodas, freios ou componentes de direção.",
["Em certa velocidade", "Somente ao frear", "Ao acelerar"],
["Carro puxa para lado", "Pneus com desgaste", "Barulho junto da vibração"],
["Balanceamento e pneus", "Freios/discos", "Folgas em direção/suspensão"],
"Não atribua automaticamente ao balanceamento; avalie freios e folgas se o sintoma for novo ou intenso.")
add("Direção","Carro puxando para um lado","Desvio da trajetória pode envolver pneus, alinhamento, freios ou suspensão.",
["Em linha reta", "Apenas ao frear", "Após passar em buraco"],
["Pneu com pressão diferente", "Freio prende", "Volante desalinhado"],
["Calibragem e pneus", "Geometria/suspensão", "Freio arrastando"],
"Se o desvio for forte ou súbito, interrompa a condução e solicite inspeção.")
add("Freios","Pedal de freio indo ao fundo","Pode indicar perda de capacidade de frenagem e risco imediato.",
["De repente", "Após serviço", "Progressivamente"],
["Existe vazamento", "Luz de freio acesa", "Veículo demora a parar"],
["Perda de fluido", "Ar no sistema hidráulico", "Falha de componentes de frenagem"],
"Não dirija o veículo. Providencie assistência ou transporte apropriado para inspeção dos freios.",critical=True)
add("Freios","Freios fazendo ruído","Chiados e rangidos podem ter diversas origens.",
["Só ao frear", "Após chuva", "Mesmo sem frear"],
["Pedal vibra", "Freio demora a responder", "Roda esquenta muito"],
["Condição das pastilhas e discos", "Contaminação ou detritos", "Componentes arrastando"],
"Se houver perda de eficiência, superaquecimento de roda ou mudança no pedal, pare e procure assistência; não toque nas partes quentes.")
add("Freios","Carro vibrando ao frear","Vibração durante frenagem deve ser investigada.",
["Em velocidade alta", "Em frenagens leves", "Em qualquer frenagem"],
["Vibração no volante", "Vibração no pedal", "Carro puxa para lado"],
["Discos/tambores e variações de atrito", "Pneus e suspensão", "Pinças ou componentes de frenagem"],
"Evite dirigir em velocidade elevada; solicite inspeção do conjunto de freios e rodas.")
add("Pneus e rodas","Pneu perdendo pressão","Perda recorrente de pressão requer inspeção, mesmo quando lenta.",
["Em poucos minutos", "De um dia para outro", "Ao longo de semanas"],
["Prego ou parafuso visível", "Válvula danificada", "Roda parece amassada"],
["Perfuração da banda de rodagem", "Válvula e vedação", "Roda/assentamento"],
"Não utilize pneu muito vazio, com bolhas ou rasgos. Procure inspeção especializada, sem improvisar reparos estruturais.")
add("Pneus e rodas","Desgaste irregular de pneus","Padrões de desgaste podem indicar diferenças de pressão ou defeitos na suspensão.",
["Mais por dentro", "Mais por fora", "Em manchas ou ondulações"],
["Carro puxa", "Volante vibra", "Amortecedor parece ruim"],
["Calibragem inadequada", "Geometria ou componentes de suspensão", "Amortecedores/rodas"],
"Inspecione o estado e a profundidade dos sulcos; substituição e alinhamento devem seguir avaliação.",severity="routine")
add("Pneus e rodas","Vibração nas rodas em velocidade","Vibração percebida em determinada velocidade pode ter várias origens.",
["A partir de determinada velocidade", "Somente ao frear", "Após trocar pneus"],
["Volante treme", "Carro inteiro vibra", "Ocorre com ruído"],
["Balanceamento/rodas", "Pneus deformados", "Componentes de suspensão ou transmissão"],
"Reduza a velocidade, evite rodovia se a vibração for intensa e procure inspeção.")
add("Sistema elétrico","Bateria descarrega com frequência","Descargas repetidas exigem verificar bateria, consumo e recarga.",
["Durante a noite", "Após poucos dias parado", "Durante o uso"],
["Luz da bateria acende", "Acessório foi instalado", "Partida fica lenta"],
["Bateria com capacidade reduzida", "Falha de recarga/alternador", "Consumo elétrico com veículo desligado"],
"Solicite testes de bateria, alternador e corrente de repouso. Evite desconectar cabos com motor ligado.")
add("Sistema elétrico","Faróis fracos ou piscando","Oscilações podem resultar de alimentação elétrica ou conexões.",
["Em marcha lenta", "Ao acelerar", "Ao ligar acessórios"],
["Luz da bateria acende", "Painel oscila", "Cheiro de queimado"],
["Alternador/regulador", "Bateria e conexões", "Aterramento e chicotes"],
"Se houver cheiro de queimado ou aquecimento de cabos, pare em segurança e solicite inspeção.")
