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
internal fun MainActivity.history(body: LinearLayout) {
        heading(body,"Histórico de consultas","Consultas armazenadas offline no próprio aparelho.")
        val entries=database.consultations()
        body.addView(label("${entries.size} registro(s)",13f,true,C.accent));gap(body,15)
        if(entries.isEmpty()) {notice(body,"Nenhuma consulta salva. Faça uma análise e toque em 'Salvar no histórico'.");return}
        val fmt=SimpleDateFormat("dd/MM/yyyy 'às' HH:mm",Locale("pt","BR"))
        entries.forEach { e ->
            val p=panel()
            p.addView(label(e.title,17f,true));gap(p,6)
            p.addView(label("${e.vehicle}  •  ${fmt.format(Date(e.createdAt))}",12f,false,C.muted));gap(p,7)
            p.addView(label(e.level,12f,true,if(e.level=="CRÍTICO")C.danger else C.accent));gap(p,12)
            p.addView(button("Ver relatório",false){showHistory(e)});gap(p,8)
            p.addView(button("Excluir",false){
                AlertDialog.Builder(this).setTitle("Excluir consulta?").setMessage("A exclusão deste histórico não pode ser desfeita.")
                    .setNegativeButton("Cancelar",null).setPositiveButton("Excluir") {_,_-> database.deleteConsultation(e.id);render()}.show()
            })
            body.addView(p);gap(body,11)
        }
    }
internal fun MainActivity.showHistory(e: Consultation) {
        AlertDialog.Builder(this).setTitle(e.title).setMessage(e.detail)
            .setPositiveButton("Fechar",null)
            .setNeutralButton("Copiar") {_,_->
                val clipboard=getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("AutoCheck",e.detail))
                Toast.makeText(this,"Relatório copiado",Toast.LENGTH_SHORT).show()
            }.setNegativeButton("Compartilhar") {_,_->share(e.detail)}.show()
    }
internal fun MainActivity.share(content: String) {
        val intent=Intent(Intent.ACTION_SEND).apply { type="text/plain"; putExtra(Intent.EXTRA_TEXT,content) }
        startActivity(Intent.createChooser(intent,"Compartilhar relatório"))
    }
internal fun MainActivity.hideKeyboard() {
        val manager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(root.windowToken,0)
    }
