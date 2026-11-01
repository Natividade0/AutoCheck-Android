package br.com.autocheck.domain

import android.content.Context
import org.json.JSONObject
import java.text.Normalizer
import java.util.Locale

data class Choice(val label: String, val next: String, val note: String, val critical: Boolean)
data class Question(val id: String, val prompt: String, val help: String, val options: List<Choice>)
data class DiagnosticCase(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val severity: String,
    val aliases: List<String>,
    val causes: List<String>,
    val advice: String,
    val dangerSigns: String,
    val questions: List<Question>
) {
    fun question(id: String): Question? = questions.firstOrNull { it.id == id }
    fun matches(search: String): Boolean {
        val needle = normalized(search)
        if (needle.isBlank()) return true
        return (listOf(title, description, category) + aliases).any { normalized(it).contains(needle) }
    }
}

data class Answer(val questionId: String, val prompt: String, val response: String, val note: String, val critical: Boolean)

data class Report(
    val level: String,
    val heading: String,
    val text: String,
    val explanation: String,
    val isCritical: Boolean
)

private fun normalized(value: String): String = Normalizer.normalize(value.lowercase(Locale.ROOT), Normalizer.Form.NFD)
    .replace(Regex("\\p{Mn}+"), "")

class Catalog private constructor(val categories: List<String>, val cases: List<DiagnosticCase>) {
    companion object {
        fun load(context: Context): Catalog {
            val raw = context.assets.open("catalog.json").bufferedReader(Charsets.UTF_8).use { it.readText() }
            val obj = JSONObject(raw)
            fun strings(array: org.json.JSONArray): List<String> = (0 until array.length()).map { array.getString(it) }
            val entries = obj.getJSONArray("cases")
            val cases = (0 until entries.length()).map { i ->
                val item = entries.getJSONObject(i)
                val qarr = item.getJSONArray("questions")
                val questions = (0 until qarr.length()).map { q ->
                    val node = qarr.getJSONObject(q)
                    val options = node.getJSONArray("options")
                    Question(
                        id = node.getString("id"), prompt = node.getString("prompt"),
                        help = node.optString("help", ""),
                        options = (0 until options.length()).map { k ->
                            val o = options.getJSONObject(k)
                            Choice(o.getString("label"), o.getString("next"), o.optString("note", ""), o.optBoolean("critical", false))
                        }
                    )
                }
                DiagnosticCase(
                    id = item.getString("id"), category = item.getString("category"),
                    title = item.getString("title"), description = item.getString("description"),
                    severity = item.getString("severity"),
                    aliases = strings(item.getJSONArray("aliases")),
                    causes = strings(item.getJSONArray("causes")),
                    advice = item.getString("advice"), dangerSigns = item.getString("dangerSigns"),
                    questions = questions
                )
            }
            return Catalog(strings(obj.getJSONArray("categories")), cases)
        }
    }
}

class DiagnosticSession(val problem: DiagnosticCase) {
    val answers = mutableListOf<Answer>()
    var node: String = problem.questions.first().id
        private set
    val completed: Boolean get() = node == "result"

    fun choose(option: Choice) {
        if (completed) return
        val q = problem.question(node) ?: return
        if (!q.options.contains(option)) return
        answers.add(Answer(q.id, q.prompt, option.label, option.note, option.critical))
        node = option.next
    }
    fun back(): Boolean {
        if (answers.isEmpty()) return false
        val removed = answers.removeAt(answers.lastIndex)
        node = removed.questionId
        return true
    }
    fun report(): Report {
        val selectedCritical = answers.any { it.critical }
        val uncertain = answers.any { it.response == "Não tenho certeza" }
        val critical = problem.severity == "critical" || selectedCritical
        val level = when {
            critical -> "CRÍTICO"
            uncertain -> "ATENÇÃO / SINAIS NÃO DESCARTADOS"
            problem.severity == "routine" -> "ORIENTAÇÃO"
            else -> "REQUER AVALIAÇÃO"
        }
        val heading = when {
            critical -> "Interrompa o uso se houver risco e procure assistência"
            uncertain -> "Precisa de avaliação para descartar sinais de perigo"
            else -> "Hipóteses para uma avaliação profissional"
        }
        val text = when {
            critical -> "A situação informada pode exigir parada imediata em local seguro. Não continue dirigindo quando houver perda de controle, falha de frenagem, luz vermelha de óleo com o motor ligado, superaquecimento, incêndio ou vazamentos perigosos."
            uncertain -> "Você não conseguiu confirmar se existe um sinal crítico. Evite assumir que está seguro; peça avaliação antes de continuar se houver risco."
            else -> "As hipóteses abaixo são possibilidades de investigação, não um diagnóstico confirmado nem uma indicação para substituir peças."
        }
        return Report(level, heading, text, problem.description, critical)
    }
    fun shareText(vehicleName: String): String {
        val report = report()
        val a = answers.joinToString("\n") { "• ${it.prompt} ${it.response}" }
        val c = problem.causes.joinToString("\n") { "• $it" }
        return "AUTOCHECK — RELATÓRIO ORIENTATIVO\nVeículo: $vehicleName\nSintoma: ${problem.title}\n" +
            "Classificação: ${report.level}\n\nRespostas:\n$a\n\nPossibilidades para investigar:\n$c\n\n" +
            "Orientação: ${problem.advice}\n\nSinais de perigo: ${problem.dangerSigns}.\n\n" +
            "AVISO: conteúdo preliminar; não substitui avaliação de mecânico qualificado."
    }
}
