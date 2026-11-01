#!/usr/bin/env python3
"""Converte fichas de sintomas locais em catalog.json, sem serviços externos."""
import ast
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SOURCE = ROOT / 'tools/catalog_parts'
CATEGORIES = ['Motor e desempenho','Partida','Lubrificação','Arrefecimento','Combustível','Ignição','Câmbio','Embreagem','Suspensão','Direção','Freios','Pneus e rodas','Sistema elétrico','Painel e alertas','Climatização','Escape e emissões']
WARNING = 'perda de capacidade de frear ou dirigir, luz de pressão do óleo acesa, superaquecimento, fumaça, vazamento de combustível ou risco de incêndio'
entries = []
for part in sorted(SOURCE.glob('part_*.py')):
    for node in ast.parse(part.read_text(encoding='utf-8')).body:
        if not isinstance(node, ast.Expr) or not isinstance(node.value, ast.Call):
            continue
        call = node.value
        if not isinstance(call.func, ast.Name) or call.func.id != 'add':
            continue
        args = [ast.literal_eval(arg) for arg in call.args]
        kwargs = {kw.arg: ast.literal_eval(kw.value) for kw in call.keywords}
        cat, title, description, contexts, clues, causes, advice = args
        assert cat in CATEGORIES
        assert len(contexts) == len(clues) == len(causes) == 3
        entries.append((cat,title,description,contexts,clues,causes,advice,kwargs))

catalog = []
for i, (cat,title,description,contexts,clues,causes,advice,kw) in enumerate(entries, 1):
    q1 = {'id':'q1','prompt':'Quando o problema acontece?', 'help':'', 'options':[
        {'label':value,'next':'q2','note':'Circunstância informada: '+value} for value in contexts]}
    q2 = {'id':'q2','prompt':'Qual outro sinal você percebe?', 'help':'', 'options':[
        {'label':value,'next':'q3','note':'Sintoma adicional informado: '+value} for value in clues] +
        [{'label':'Não sei / nenhum destes','next':'q3','note':'Sinal adicional não confirmado'}]}
    q3 = {'id':'q3','prompt':'Existe algum sinal de perigo?',
          'help':'Preste atenção a sinais como: '+WARNING,
          'options':[
              {'label':'Sim, percebi um desses sinais','next':'result','note':'Sinal potencialmente grave informado','critical':True},
              {'label':'Não percebi esses sinais','next':'result','note':'Não identificou sinais críticos perguntados'},
              {'label':'Não tenho certeza','next':'result','note':'Não foi possível excluir condições perigosas'}]}
    catalog.append({'id':f'AC-{i:04d}','category':cat,'title':title,
        'description':description,'severity':'critical' if kw.get('critical') else kw.get('severity','attention'),
        'aliases':kw.get('aliases',[]),'causes':causes,'advice':advice,
        'dangerSigns':WARNING,'questions':[q1,q2,q3],
        'reviewStatus':'Conteúdo preliminar, ainda sem revisão mecânica'} )
assert len(catalog) == 48, f'Esperados 48, encontrados {len(catalog)}'
target = ROOT / 'app/src/main/assets/catalog.json'
target.parent.mkdir(parents=True,exist_ok=True)
target.write_text(json.dumps({'schemaVersion':1,'catalogVersion':'1.0-beta',
        'categories':CATEGORIES,'cases':catalog},ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
print('AutoCheck:',len(catalog),'situações e',len(catalog)*36,'caminhos de respostas')
