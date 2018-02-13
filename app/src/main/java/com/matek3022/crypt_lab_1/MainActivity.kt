package com.matek3022.crypt_lab_1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private lateinit var etCode: EditText
    private lateinit var etOutput: EditText
    private lateinit var etDecryptOutput: EditText
    private lateinit var cryptBt: Button
    private lateinit var decryptBt: Button
    private lateinit var brutBt: Button
    private lateinit var list: RecyclerView
    private lateinit var listWords: ArrayList<Char>
    private lateinit var brutWords: ArrayList<String>
    private lateinit var listAdapter: RVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        cryptBt.setOnClickListener { etOutput.setText(crypt(etInput.text.toString(), getCode())) }
        decryptBt.setOnClickListener { etDecryptOutput.setText(decrypt(etOutput.text.toString(), getCode())) }
        brutBt.setOnClickListener { brutAtack() }
    }

    private fun getCode(): Int {
        return if (etCode.text.toString().isEmpty()) {
            0
        } else {
            Integer.valueOf(etCode.text.toString())
        }
    }

    private fun init() {
        etInput = findViewById<EditText>(R.id.input)
        etCode = findViewById<EditText>(R.id.code)
        etOutput = findViewById<EditText>(R.id.output)
        etDecryptOutput = findViewById<EditText>(R.id.decryptOutput)
        cryptBt = findViewById<Button>(R.id.crypt)
        decryptBt = findViewById<Button>(R.id.decrypt)
        brutBt = findViewById<Button>(R.id.brut)
        list = findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        listWords = ArrayList()
        brutWords = ArrayList()
        listAdapter = RVAdapter(brutWords)
        list.adapter = listAdapter
        with(listWords) {
            add('А')
            add('Б')
            add('В')
            add('Г')
            add('Д')
            add('Е')
            add('Ё')
            add('Ж')
            add('З')
            add('И')
            add('Й')
            add('К')
            add('Л')
            add('М')
            add('Н')
            add('О')
            add('П')
            add('Р')
            add('С')
            add('Т')
            add('У')
            add('Ф')
            add('Х')
            add('Ц')
            add('Ч')
            add('Ш')
            add('Щ')
            add('Ъ')
            add('Ы')
            add('Ь')
            add('Э')
            add('Ю')
            add('Я')
        }
    }

    fun brutAtack() {
        brutWords.clear()
        for ((index, value) in listWords.withIndex()) {
            brutWords.add(decrypt(etOutput.text.toString(), index))
        }
        listAdapter.notifyDataSetChanged()
    }

    fun crypt(text: String, otherKey: Int = 0): String {
        var key = otherKey
        key %= listWords.size
        val res = text.toCharArray()
        for ((index, value) in res.withIndex()) {
            for ((lindex, lvalue) in listWords.withIndex()) {
                if (lindex + key >= listWords.size) {
                    if (value.equals(lvalue, true)) {
                        res[index] = listWords[lindex + key + 1 - listWords.size]
                    }
                } else {
                    if (value.equals(lvalue, true)) {
                        res[index] = listWords[lindex + key]
                    }
                }
            }
        }
        return String(res)
    }

    fun decrypt(cryptText: String, otherKey: Int = 0): String {
        var key = otherKey
        key %= listWords.size
        val res = cryptText.toCharArray()
        for ((index, value) in res.withIndex()) {
            for ((lindex, lvalue) in listWords.withIndex()) {
                if (lindex - key < 0) {
                    if (value.equals(lvalue, true)) {
                        res[index] = listWords[lindex - key - 1 + listWords.size]
                    }
                } else {
                    if (value.equals(lvalue, true)) {
                        res[index] = listWords[lindex - key]
                    }
                }
            }
        }
        return String(res)
    }

    class RVAdapter(private val bruteStrings: ArrayList<String>): RecyclerView.Adapter<RVAdapter.RVHolder>() {
        
        override fun onBindViewHolder(holder: RVHolder?, position: Int) {
            holder?.text?.text = bruteStrings[position]
            holder?.index?.text = position.toString()
        }

        override fun getItemCount(): Int {
            return bruteStrings.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RVHolder {
            val inflatedView = parent?.inflate(R.layout.item, false)
            return RVHolder(inflatedView!!)
        }

        class RVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var text: TextView? = null
            var index: TextView? = null
            init {
                text = itemView.findViewById(R.id.text)
                index = itemView.findViewById(R.id.index)
            }
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}