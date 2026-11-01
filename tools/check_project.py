#!/usr/bin/env python3
"""Validação estrutural do projeto sem Android SDK (NÃO compila APK)."""
import ast
import subprocess
import json
import xml.etree.ElementTree as ET
from pathlib import Path
root=Path(__file__).resolve().parents[1]
for xml in (root/'app/src/main').rglob('*.xml'):
    ET.parse(xml)
manifest=(root/'app/src/main/AndroidManifest.xml').read_text(encoding='utf8')
assert 'android.permission.INTERNET' not in manifest
assert 'android:exported="true"' in manifest
assert 'android:allowBackup="false"' in manifest
assert 'android:icon="@drawable/ic_launcher"' in manifest
for s in (root/'tools').glob('*.py'):
    ast.parse(s.read_text(encoding='utf8'))
for filename in ('domain/Diagnostic.kt','ui/MainActivity.kt','data/LocalDatabase.kt'):
    code=(root/'app/src/main/java/br/com/autocheck'/filename).read_text(encoding='utf8')
    assert code.startswith('package br.com.autocheck.')
    assert code.count('class ')>=1
    assert len(code)>100
settings=(root/'app/build.gradle.kts').read_text(encoding='utf8')
assert 'applicationId = "br.com.autocheck.app"' in settings
assert 'minSdk = 26' in settings
assert 'targetSdk = 35' in settings
assert 'versionCode = 1' in settings
workflow=(root/'.github/workflows/android.yml').read_text(encoding='utf8')
assert 'assembleDebug' in workflow and 'upload-artifact' in workflow
subprocess.run(['python',str(root/'tools/validate_catalog.py')],check=True)
print('OK: arquivos obrigatórios, XML, Python, permissões e referências de build presentes.')
print('Limite: este teste NÃO verifica compilação Kotlin/Android nem execução em dispositivo.')
