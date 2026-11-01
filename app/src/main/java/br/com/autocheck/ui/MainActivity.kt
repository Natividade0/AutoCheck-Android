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
class MainActivity : Activity() {
    internal lateinit var catalog: Catalog
    internal lateinit var database: LocalDatabase
    internal lateinit var root: LinearLayout
    internal var page = "home"
    internal var session: DiagnosticSession? = null
    internal var selectedVehicle: Long = -1L
    internal var query: String = ""
    internal var category: String? = null
    internal val prefs by lazy { getSharedPreferences("autocheck_prefs", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = C.bg
        window.navigationBarColor = C.bg
        window.decorView.systemUiVisibility = 0
        database = LocalDatabase(this)
        catalog = try { Catalog.load(this) } catch (e: Exception) {
            AlertDialog.Builder(this).setTitle("Erro ao carregar diagnósticos")
                .setMessage("O catálogo local não pôde ser aberto: ${e.message}")
                .setPositiveButton("Fechar") { _, _ -> finish() }.show()
            return
        }
        selectedVehicle = prefs.getLong("selected_vehicle", -1L)
        page = savedInstanceState?.getString("page") ?: "home"
        query = savedInstanceState?.getString("query") ?: ""
        category = savedInstanceState?.getString("category")
        val savedCase = savedInstanceState?.getString("caseId")
        if (savedCase != null) {
            val problem = catalog.cases.firstOrNull { it.id == savedCase }
            if (problem != null) {
                session = DiagnosticSession(problem)
                val selected = savedInstanceState?.getStringArrayList("responses") ?: arrayListOf()
                selected.forEach { response ->
                    val s = session ?: return@forEach
                    val q = s.problem.question(s.node)
                    val choice = q?.options?.firstOrNull { it.label == response }
                    if (choice != null) s.choose(choice)
                }
            }
        }
        root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(C.bg) }
        setContentView(root)
        render()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("page", page)
        outState.putString("query", query)
        outState.putString("category", category)
        outState.putString("caseId", session?.problem?.id)
        outState.putStringArrayList("responses", ArrayList(session?.answers?.map { it.response } ?: emptyList()))
        super.onSaveInstanceState(outState)
    }
    override fun onDestroy() { database.close(); super.onDestroy() }
    override fun onBackPressed() {
        val current = session
        when {
            page == "diagnosis" && current != null && current.answers.isNotEmpty() -> { current.back(); render() }
            page == "diagnosis" -> { session = null; go("diagnose") }
            page != "home" -> go("home")
            else -> super.onBackPressed()
        }
    }
}
