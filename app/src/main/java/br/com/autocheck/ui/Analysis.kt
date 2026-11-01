package br.com.autocheck.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import br.com.autocheck.data.LocalDatabase
import br.com.autocheck.data.Vehicle
import br.com.autocheck.data.Consultation
import br.com.autocheck.domain.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Aplicativo Android nativo, sem rede, sem permissões sensíveis e sem dependências visuais externas. */
internal fun MainActivity.diagnosis(body: LinearLayout) {
        val s = session ?: run { go("diagnose");return }
        body.addView(label("${s.problem.category}  •  ${s.problem.id}",12f,true,C.accent));gap(body,6)
        heading(body,s.problem.title,s.problem.description)
        if(s.completed) { resultScreen(body,s);return }
        val index = s.answers.size + 1
        val total = s.problem.questions.size
        body.addView(label("PERGUNTA $index DE $total",12f,true,C.accent));gap(body,10)
        val bar = View(this).apply { background=bg(C.accent,5) }
        val progress = LinearLayout(this).apply { background=bg(C.raised,6);addView(bar,LinearLayout.LayoutParams(0,d(6),index.toFloat()))
            addView(View(this),LinearLayout.LayoutParams(0,d(6),(total-index).toFloat())) }
        body.addView(progress,LinearLayout.LayoutParams(-1,d(6)));gap(body,18)
        val question = s.problem.question(s.node)
        if(question==null) {notice(body,"Houve uma inconsistência no fluxo de perguntas. Reinicie a consulta.",true);return}
        val qPanel = panel()
        qPanel.addView(label(question.prompt,20f,true))
        if(question.help.isNotBlank()) { gap(qPanel,12);qPanel.addView(label(question.help,14f,false,C.warning)) }
        body.addView(qPanel);gap(body,15)
        question.options.forEach { option ->
            val optionView = itemButton(option.label,if(option.critical)"Possível situação de risco" else null) {s.choose(option);render()}
            if(option.critical) optionView.background = bg(Color.rgb(68,35,41),16)
            body.addView(optionView);gap(body,10)
        }
        gap(body,7)
        if(s.answers.isNotEmpty())body.addView(button("← Voltar à pergunta anterior",false){s.back();render()})
        gap(body,15)
        notice(body,"Responda apenas o que realmente observou. Não faça testes arriscados para confirmar sintomas.")
    }
internal fun MainActivity.resultScreen(body: LinearLayout, s: DiagnosticSession) {
        val report=s.report()
        val p=panel()
        p.background=bg(if(report.isCritical)Color.rgb(68,35,41) else C.raised,16)
        p.addView(label("RESULTADO • ${report.level}",12f,true,if(report.isCritical)C.danger else C.accent));gap(p,10)
        p.addView(label(report.heading,20f,true));gap(p,10)
        p.addView(label(report.text,14f));body.addView(p);gap(body,16)
        body.addView(label("Possíveis causas a investigar",18f,true));gap(body,10)
        s.problem.causes.forEachIndexed { i,cause ->
            val cp = panel(14); cp.addView(label("${i+1}. $cause",15f,true)); gap(cp,5); cp.addView(label("Hipótese orientativa; sem ordem de probabilidade",12f,false,C.muted)); body.addView(cp); gap(body,8)
        }
        gap(body,6)
        heading(body,"Próximos passos")
        val adv=panel();adv.addView(label(s.problem.advice,15f));body.addView(adv);gap(body,16)
        body.addView(label("O que você informou",18f,true));gap(body,10)
        s.answers.forEach {a ->
            val ap=panel(12);ap.addView(label(a.prompt,13f,false,C.muted));gap(ap,4)
            ap.addView(label(a.response,14f,true));body.addView(ap);gap(body,8)
        }
        gap(body,12)
        notice(body,"Sinais críticos relacionados ao tema: ${s.problem.dangerSigns}. Se houver perigo, interrompa o uso do veículo de modo seguro e procure ajuda.",report.isCritical)
        gap(body,14)
        body.addView(button("Salvar no histórico"){
            database.saveConsultation(vehicleName(),s.problem.id,s.problem.title,report.level,s.shareText(vehicleName()))
            Toast.makeText(this,"Consulta salva neste aparelho",Toast.LENGTH_SHORT).show()
            go("history")
        });gap(body,9)
        body.addView(button("Compartilhar relatório",false){ share(s.shareText(vehicleName())) });gap(body,9)
        body.addView(button("Recomeçar consulta",false){start(s.problem)})
    }
