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
internal object C {
    val bg = Color.rgb(11, 18, 32)
    val surface = Color.rgb(19, 31, 49)
    val raised = Color.rgb(27, 43, 63)
    val text = Color.rgb(238, 246, 250)
    val muted = Color.rgb(167, 186, 202)
    val accent = Color.rgb(22, 204, 180)
    val warning = Color.rgb(252, 198, 91)
    val danger = Color.rgb(255, 131, 130)
}

internal fun MainActivity.d(value: Int): Int = (resources.displayMetrics.density * value + 0.5f).toInt()
internal fun MainActivity.bg(color: Int, radius: Int = 16, border: Int? = null): GradientDrawable = GradientDrawable().apply {
        setColor(color); cornerRadius = d(radius).toFloat()
        if (border != null) setStroke(d(1), border)
    }
internal fun MainActivity.col(padding: Int = 0): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(d(padding),d(padding),d(padding),d(padding))
    }
internal fun MainActivity.row(): LinearLayout = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
internal fun MainActivity.label(value: String, size: Float = 15f, bold: Boolean = false, color: Int = C.text): TextView = TextView(this).apply {
        text = value; textSize = size; setTextColor(color)
        typeface = Typeface.create(if (bold) "sans-serif-medium" else "sans-serif", Typeface.NORMAL)
        setLineSpacing(d(2).toFloat(),1f)
    }
internal fun MainActivity.panel(padding: Int = 16): LinearLayout = col(padding).apply { background = bg(C.surface,18) }
internal fun MainActivity.gap(parent: LinearLayout, height: Int = 12) { parent.addView(View(this),LinearLayout.LayoutParams(1,d(height))) }
internal fun MainActivity.heading(parent: LinearLayout, title: String, subtitle: String? = null) {
        parent.addView(label(title,23f,true))
        if (subtitle != null) { gap(parent,5);parent.addView(label(subtitle,13f,false,C.muted)) }
        gap(parent,16)
    }
internal fun MainActivity.button(title: String, primary: Boolean = true, action: () -> Unit): TextView = label(title,15f,true,if (primary) C.bg else C.text).apply {
        gravity = Gravity.CENTER
        setPadding(d(15),d(14),d(15),d(14))
        background = bg(if (primary) C.accent else C.raised,12)
        isClickable = true;isFocusable = true
        setOnClickListener { action() }
    }
internal fun MainActivity.itemButton(title: String, subtitle: String? = null, action: () -> Unit): LinearLayout = panel(15).apply {
        addView(label(title,16f,true))
        if (subtitle != null) { gap(this,5);addView(label(subtitle,13f,false,C.muted)) }
        isClickable = true;isFocusable = true
        setOnClickListener { action() }
    }
internal fun MainActivity.field(hint: String, initial: String = "", numeric: Boolean = false): EditText = EditText(this).apply {
        setText(initial);setSingleLine(true);setTextColor(C.text);setHintTextColor(C.muted);setHint(hint)
        textSize = 15f
        setPadding(d(13),d(12),d(13),d(12));background = bg(C.raised,10)
        if (numeric) inputType = android.text.InputType.TYPE_CLASS_NUMBER
    }
internal fun MainActivity.go(target: String) { page = target;render() }
internal fun MainActivity.selected(): Vehicle? = database.vehicles().firstOrNull { it.id == selectedVehicle }
internal fun MainActivity.vehicleName() = selected()?.name ?: "Não cadastrado"
