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
internal fun MainActivity.home(body: LinearLayout) {
        banner(body)
        val vehicle = selected()
        val p = panel()
        p.addView(label("VEÍCULO ATIVO",11f,true,C.accent));gap(p,7)
        p.addView(label(vehicle?.name ?: "Nenhum veículo cadastrado",19f,true));gap(p,5)
        p.addView(label(vehicle?.let { "${it.engine.ifBlank { "Motor não informado" }}  •  ${it.mileage} km" } ?: "Adicione seu carro para identificar as consultas.",13f,false,C.muted))
        gap(p,12);p.addView(button(if(vehicle==null)"Cadastrar veículo" else "Trocar / editar veículo",false){go("vehicles")})
        body.addView(p);gap(body,15)
        body.addView(button("Começar diagnóstico  →") { query="";category=null;go("diagnose") });gap(body,15)
        val stats = panel();stats.addView(label("Biblioteca inicial",18f,true));gap(stats,10)
        stats.addView(label("${catalog.cases.size} situações orientativas em ${catalog.categories.size} sistemas do veículo.",14f,false,C.muted))
        gap(stats,12);stats.addView(label("Consultas salvas neste aparelho: ${database.consultations().size}",13f,false,C.accent))
        body.addView(stats);gap(body,15)
        heading(body,"Acesso rápido")
        listOf("Motor falhando ou engasgando","Luz vermelha do óleo com motor ligado","Pedal de freio indo ao fundo","Temperatura do motor muito alta")
            .forEach { title ->
                val problem = catalog.cases.firstOrNull { it.title == title }
                if (problem != null) { body.addView(itemButton(problem.title,problem.category){start(problem)});gap(body,8) }
            }
        gap(body,7)
        notice(body,"Esta versão utiliza conteúdo técnico preliminar que ainda precisa de revisão especializada. O aplicativo apresenta hipóteses, não confirma defeitos nem substitui inspeção profissional.")
    }
internal fun MainActivity.diagnose(body: LinearLayout) {
        heading(body,"Encontrar um problema","Busque pela descrição do sintoma ou escolha o sistema do carro.")
        val search = field("Pesquisar: falha, ruído, freio, óleo...",query)
        body.addView(search);gap(body,12)
        val categoriesLine = panel(12)
        categoriesLine.addView(label("Sistema do veículo",13f,true));gap(categoriesLine,9)
        val selectedCategory = label(category ?: "Todos os sistemas",14f,false,C.accent)
        categoriesLine.addView(selectedCategory)
        categoriesLine.isClickable = true
        categoriesLine.setOnClickListener {
            val names = listOf("Todos os sistemas") + catalog.categories
            AlertDialog.Builder(this).setTitle("Filtrar categoria").setItems(names.toTypedArray()) { _, index ->
                category = if(index==0) null else names[index]
                hideKeyboard();render()
            }.show()
        }
        body.addView(categoriesLine);gap(body,13)
        val results = col()
        body.addView(results)
        fun update() {
            results.removeAllViews()
            val matches = catalog.cases.filter { (category==null || it.category==category) && it.matches(query) }
            results.addView(label("${matches.size} situação(ões) encontrada(s)",12f,true,C.muted))
            gap(results,10)
            if(matches.isEmpty()) {
                notice(results,"Nenhum sintoma cadastrado corresponde à busca. Tente outro termo. A ausência no catálogo não significa ausência de risco.")
            } else matches.forEach { problem ->
                results.addView(itemButton(problem.title, "${problem.category}  •  ${problem.description}") { hideKeyboard();start(problem) })
                gap(results,9)
            }
        }
        search.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start:Int, count:Int, after:Int) {}
            override fun onTextChanged(s:CharSequence?, start:Int, before:Int, count:Int) {query=s?.toString() ?: "";update()}
            override fun afterTextChanged(s:Editable?) {}
        })
        update()
    }
internal fun MainActivity.start(problem: DiagnosticCase) { session=DiagnosticSession(problem);page="diagnosis";render() }
