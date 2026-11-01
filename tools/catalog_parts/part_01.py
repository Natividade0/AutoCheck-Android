add("Motor e desempenho","Motor falhando ou engasgando","Falhas de funcionamento podem ter causas em diversos sistemas.",
["Ao acelerar", "Em marcha lenta", "Quando está frio"],
["A luz da injeção acendeu", "Sinto trepidação", "O consumo aumentou"],
["Sistema de ignição", "Alimentação de combustível", "Entrada de ar ou sensores"],
"Evite exigir desempenho do motor; informe quando a falha aparece e procure diagnóstico com equipamentos apropriados.",aliases=["carro engasgando","motor cortando"])
add("Motor e desempenho","Motor sem força","A perda de potência pode comprometer ultrapassagens e subidas.",
["Nas subidas", "Em qualquer marcha", "Só com ar-condicionado ligado"],
["Motor sobe giro mas não acelera", "Existe fumaça", "A luz da injeção acendeu"],
["Falhas de alimentação ou admissão", "Problema de ignição ou sensores", "Possível patinação da transmissão"],
"Não tente compensar a perda de potência dirigindo de forma agressiva; procure inspeção.")
add("Motor e desempenho","Motor morrendo sozinho","Desligamentos inesperados podem comprometer manobras e a condução.",
["Quando paro no semáforo", "Em movimento", "Ao ligar acessórios"],
["Oscila marcha lenta", "Ocorre com motor quente", "Demora a pegar depois"],
["Controle de marcha lenta/admissão", "Alimentação elétrica ou combustível", "Sensores ou gerenciamento do motor"],
"Se o motor apagar em circulação, pare em local seguro e não continue utilizando até avaliação, especialmente se isso se repetir.",severity="attention")
add("Partida","Não dá partida e só faz clique","Um clique sem girar o motor pode ter várias causas elétricas ou mecânicas.",
["Depois de ficar parado", "De repente", "Após uso de acessórios"],
["Luzes do painel ficam fracas", "Cliques repetidos", "Luzes permanecem fortes"],
["Bateria com carga insuficiente", "Conexões elétricas ou aterramento", "Motor de partida/solenoide"],
"Não faça ligações improvisadas ou curto em terminais. Peça teste da bateria e do circuito de partida.")
add("Partida","Motor gira mas não pega","O motor de partida funciona, mas não ocorre a partida do veículo.",
["Só a frio", "Só a quente", "Em qualquer situação"],
["Cheiro de combustível", "Luz de imobilizador acesa", "Antes já falhava"],
["Sistema de combustível", "Sistema de ignição", "Sensores ou imobilizador"],
"Evite insistir longamente na partida; solicite verificação da alimentação, centelha e códigos quando aplicável.")
add("Partida","Partida demorada","O motor demora mais que o habitual para funcionar.",
["Pela manhã", "Depois de abastecer", "Com motor aquecido"],
["Gira devagar", "Pega e morre", "Há odor de combustível"],
["Estado da bateria ou circuito de partida", "Pressão/alimentação de combustível", "Sensores de temperatura ou gerenciamento"],
"Registre quando acontece e solicite testes antes de substituir peças.")
add("Lubrificação","Luz vermelha do óleo com motor ligado","Pode indicar pressão de óleo inadequada e risco de dano severo ao motor.",
["Em marcha lenta", "Ao acelerar", "Permanece acesa continuamente"],
["Ouço batidas metálicas", "Há óleo vazando", "Sem outros sinais"],
["Baixa pressão real de lubrificação", "Nível de óleo ou vazamento", "Sensor/chicote de pressão"],
"Pare em local seguro, desligue o motor e não volte a utilizá-lo antes de verificar a causa. A leitura do nível não confirma pressão adequada.",critical=True,aliases=["luz óleo acesa","luz pressão óleo"])
add("Lubrificação","Vazamento de óleo do motor","Óleo no chão ou no motor exige identificação da origem e do nível.",
["Após ficar estacionado", "Com motor funcionando", "Depois da troca de óleo"],
["Pingos no chão", "Cheiro de óleo queimado", "Luz de pressão acendeu"],
["Juntas ou retentores", "Filtro ou bujão", "Tubulações ou componentes de lubrificação"],
"Se houver perda intensa, luz de pressão acesa ou óleo sobre área quente, pare e busque avaliação sem dirigir.")
add("Lubrificação","Motor consumindo óleo","Queda repetida no nível pode envolver vazamentos ou consumo interno.",
["Entre trocas regulares", "Em viagens", "Depois de reparo"],
["Fumaça azulada", "Manchas no piso", "Não observo vazamentos"],
["Vazamento externo", "Vedação interna ou anéis", "Sistema de ventilação do cárter"],
"Acompanhe o nível conforme o manual e solicite investigação; não use aditivos como substituto de diagnóstico.")
