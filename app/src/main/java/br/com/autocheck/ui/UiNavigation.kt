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

internal fun MainActivity.render() {
        root.removeAllViews()
    val content = ScrollView(this).apply { fillViewport = false; clipToPadding = false }
    val body = col(18).apply { setPadding(d(18),d(20),d(18),d(30)) }
        content.addView(body)
        root.addView(content,LinearLayout.LayoutParams(-1,0,1f))
        when (page) {
            "home" -> home(body)
            "diagnose" -> diagnose(body)
            "diagnosis" -> diagnosis(body)
            "vehicles" -> vehicles(body)
            "history" -> history(body)
            else -> home(body)
        }
        root.addView(navigation(),LinearLayout.LayoutParams(-1,d(69)))
    }
internal fun MainActivity.navigation(): LinearLayout = row().apply {
        setPadding(d(10),d(7),d(10),d(7));setBackgroundColor(C.surface)
        listOf("home" to "Início", "diagnose" to "Diagnóstico", "vehicles" to "Veículos", "history" to "Histórico")
            .forEach { (id,title) ->
            val current = page == id || (page == "diagnosis" && id == "diagnose")
            val tab = label(title,12f,current,if(current)C.accent else C.muted).apply {
                    gravity = Gravity.CENTER
                    setPadding(d(3),d(7),d(3),d(7))
                    if(current) background=bg(C.raised,12)
                    isClickable = true
                    setOnClickListener { if (id != "diagnose") session = null;go(id) }
                }
                addView(tab,LinearLayout.LayoutParams(0,-1,1f))
            }
    }
internal fun MainActivity.banner(body: LinearLayout) {
        body.addView(label("AUTOCHECK  •  BETA OFFLINE",12f,true,C.accent));gap(body,6)
        body.addView(label("Entenda seu carro",27f,true));gap(body,6)
        body.addView(label("Orientações automotivas guiadas, sem internet e sem custos por consulta.",14f,false,C.muted))
        gap(body,20)
    }
internal fun MainActivity.notice(body: LinearLayout, text: String, dangerous: Boolean = false) {
    val p = panel(14).apply { background = bg(if(dangerous) Color.rgb(68,35,41) else C.raised,14) }
        p.addView(label(if(dangerous)"⚠  Atenção à segurança" else "ⓘ  Informação importante",14f,true,if(dangerous)C.danger else C.accent))
        gap(p,7);p.addView(label(text,13f,false,C.text));body.addView(p)
    }
