import json
from pathlib import Path
p=Path(__file__).resolve().parents[1]/'app/src/main/assets/catalog.json'
a=json.loads(p.read_text(encoding='utf-8'))
assert len(a['categories'])==16
assert len(a['cases'])==48
assert len({c['id'] for c in a['cases']})==48
for c in a['cases']:
    assert c['title'] and c['causes'] and c['advice'] and c['dangerSigns']
    q={x['id']:x for x in c['questions']}
    assert set(q)=={'q1','q2','q3'}
    assert all(x['options'] for x in q.values())
    assert all(o['next'] in {'q2','q3','result'} for x in q.values() for o in x['options'])
print('Biblioteca estruturalmente válida:',len(a['cases']),'casos.')
