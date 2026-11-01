add("Arrefecimento","Temperatura do motor muito alta","Superaquecimento pode causar danos graves e risco de queimadura.",
["No trânsito parado", "Em rodovia", "Em subidas"],
["Sai vapor", "Ventoinha não parece acionar", "Percebo perda de líquido"],
["Vazamento ou baixo nível do sistema", "Ventoinha e seu comando", "Termostato, bomba ou circulação"],
"Pare em local seguro, desligue o motor e deixe esfriar. Nunca abra reservatório ou tampa pressurizada enquanto estiver quente.",critical=True,aliases=["carro fervendo","motor aquecendo"])
add("Arrefecimento","Líquido de arrefecimento baixando","A perda de fluido pode ocorrer mesmo sem poças aparentes.",
["Após dirigir", "Depois de estacionar", "Continuamente"],
["Mancha no piso", "Odor adocicado", "Motor aquece mais"],
["Mangueiras ou conexões", "Radiador/reservatório", "Vazamento interno a investigar"],
"Não abra o sistema quente. Se houver aquecimento ou perda importante, evite conduzir e procure inspeção.")
add("Arrefecimento","Ventoinha funciona sem parar","O acionamento prolongado pode ser normal em algumas condições, mas também indicar falhas.",
["Com ar-condicionado ligado", "Depois de desligar o motor", "Com motor ainda frio"],
["Indicador de temperatura alto", "Ar-condicionado não refrigera", "Sem outros sinais"],
["Condições normais de controle térmico", "Sensores e relés", "Arrefecimento insuficiente"],
"Confira o manual e procure avaliação se houver aquecimento ou comportamento novo persistente.")
add("Combustível","Consumo de combustível elevado","Aumento de consumo pode depender de percurso, condução ou falha mecânica.",
["Depois de abastecer", "Progressivamente", "Só na cidade"],
["O motor falha", "Pneus parecem baixos", "A luz da injeção acendeu"],
["Condições de percurso e pneus", "Ignição/alimentação", "Sensores e gerenciamento do motor"],
"Registre consumo por abastecimentos completos antes de concluir que existe defeito.",severity="routine")
add("Combustível","Cheiro forte de combustível","Odores persistentes podem indicar vazamento ou evaporação inadequada.",
["Após abastecer", "Com veículo parado", "Com motor ligado"],
["Há líquido visível", "Odor dentro do carro", "Apareceu após reparo"],
["Vazamento em linhas ou conexões", "Sistema de ventilação do tanque", "Vedação de componentes"],
"Se o odor for intenso ou houver combustível aparente, desligue o motor em local seguro, afaste fontes de ignição e solicite atendimento sem dirigir.",critical=True)
add("Combustível","Carro falha após abastecimento","Falhas logo após abastecer podem ter relação temporal, mas não provam combustível adulterado.",
["Logo ao sair do posto", "No dia seguinte", "Somente com tanque cheio"],
["Motor engasga", "Partida difícil", "Luz da injeção acesa"],
["Qualidade do combustível a verificar", "Sistema de evaporação/ventilação", "Falhas preexistentes de injeção ou ignição"],
"Guarde o comprovante e procure diagnóstico antes de atribuir a causa ao posto.")
add("Ignição","Luz da injeção piscando","Uma luz de falha do motor piscando pode indicar condição que requer atenção imediata.",
["Ao acelerar", "Em marcha lenta", "Durante todo percurso"],
["Motor trepida muito", "Perda de força", "Cheiro de combustível"],
["Falhas de combustão", "Sistema de ignição", "Injeção ou mistura"],
"Reduza a solicitação do motor e pare em local seguro. Evite continuar conduzindo; procure assistência para ler falhas e verificar o motor.",critical=True)
add("Ignição","Marcha lenta oscilando","A rotação varia com o veículo parado.",
["Só com motor frio", "Com ar-condicionado", "Sempre"],
["Motor apaga", "Trepidação", "Luz da injeção acesa"],
["Entrada de ar não medida", "Corpo de borboleta/controle", "Sensores ou falhas de ignição"],
"Procure diagnóstico orientado a sintomas antes de limpar ou trocar componentes.")
add("Ignição","Motor trepidando parado","Vibrações podem vir do funcionamento do motor ou de seus apoios.",
["Em marcha lenta", "Ao engatar marcha", "Com ar-condicionado"],
["Rotação irregular", "Ruído ao acelerar", "Sem falhas de rotação"],
["Coxins do motor/câmbio", "Combustão irregular", "Componentes de apoio ou escapamento"],
"Se houver vibração forte, perda de potência ou falha de combustão, evite utilizar até avaliação.")
