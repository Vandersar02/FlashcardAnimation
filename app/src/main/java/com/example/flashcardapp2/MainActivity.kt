package com.example.flashcardapp2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private val addRequest = 1
    private val editRequest = 2
    private var currentFlashcardIndex = 0
    private var allFlashcards = mutableListOf<Flashcard>()
    private var countDownTimer: CountDownTimer? = null
    private lateinit var rightOutAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rightOutAnim = AnimationUtils.loadAnimation(this, R.anim.right_in)

        val questionText = findViewById<TextView>(R.id.question_txt)
        val answerText1 = findViewById<TextView>(R.id.answer1_txt)
        val answerText2 = findViewById<TextView>(R.id.answer2_txt)
        val answerText3 = findViewById<TextView>(R.id.answer3_txt)
        val confetti = findViewById<KonfettiView>(R.id.konfetti_View)

        val plusBtn = findViewById<View>(R.id.plusBtn)
        val editBtn = findViewById<View>(R.id.editBtn)
        val btnNext = findViewById<View>(R.id.btnNext)
        val btnDelete = findViewById<View>(R.id.btnDelete)

        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.5)
        )

        rightOutAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {   }
            override fun onAnimationEnd(animation: Animation?) {   }
            override fun onAnimationRepeat(animation: Animation?) {  }
        })

        countDownTimer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timer).text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                btnNext.performClick()
            }
        }

        // Initialisez votre base de données et récupérez les flashcards
        val flashcardDatabase = FlashcardDatabase(this)
        flashcardDatabase.initFirstCard()
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        // afficher tous les élements de la base de données
        displayFlashcard(currentFlashcardIndex)

        answerText1.setOnClickListener {
            if ((answerText1.text).isNotEmpty()) {
                if (answerText1.text == allFlashcards[currentFlashcardIndex].answer) {
                    // Bonne réponse
                    answerText1.setBackgroundColor(Color.GREEN)
                    // Déclencher l'animation de célébration ici
                    confetti.start(party)

                    // passons au question suivant
                    Handler().postDelayed({
                        answerText1.setBackgroundColor(getResources().getColor(R.color.grown)) // Remettre la couleur de fond par défaut
                        btnNext.performClick()
                    },500)
                } else {
                    // Mauvaise réponse
                    answerText1.setBackgroundColor(Color.RED)
                    vibrateDevice()
                }
                // Désactiver les autres réponses
                answerText3.isEnabled = false
                answerText2.isEnabled = false

                // Utilisation d'un Handler pour rétablir les valeurs par défaut après un délai de 2 secondes
                Handler().postDelayed({
                    // Rétablir les valeurs par défaut après 2 secondes
                    answerText1.setBackgroundColor(getResources().getColor(R.color.grown)) // Remettre la couleur de fond par défaut
                    answerText3.isEnabled = true // Activer à nouveau les autres réponses
                    answerText2.isEnabled = true
                }, 1000) // Délai de 2000 ms (2 secondes)
            }

        }

        answerText2.setOnClickListener {
            if ((answerText2.text).isNotEmpty()) {
                if (answerText2.text == allFlashcards[currentFlashcardIndex].answer) {
                    // Bonne réponse
                    answerText2.setBackgroundColor(Color.GREEN)
                    // Déclencher l'animation de célébration ici
                    confetti.start(party)

                    // passons au question suivant
                    Handler().postDelayed({
                        answerText2.setBackgroundColor(getResources().getColor(R.color.grown)) // Remettre la couleur de fond par défaut
                        btnNext.performClick()
                    },500)
                } else {
                    // Mauvaise réponse
                    answerText2.setBackgroundColor(Color.RED)
                    vibrateDevice()
                }
                // Désactiver les autres réponses
                answerText1.isEnabled = false
                answerText3.isEnabled = false

                // Utilisation d'un Handler pour rétablir les valeurs par défaut après un délai de 2 secondes
                Handler().postDelayed({
                    // Rétablir les valeurs par défaut après 2 secondes
                    answerText2.setBackgroundColor(getResources().getColor(R.color.grown)) // Remettre la couleur de fond par défaut
                    answerText1.isEnabled = true // Activer à nouveau les autres réponses
                    answerText3.isEnabled = true
                }, 1000) // Délai de 2000 ms (2 secondes)
            }
        }

        answerText3.setOnClickListener {
            if ((answerText3.text).isNotEmpty()) {
                if (answerText3.text == allFlashcards[currentFlashcardIndex].answer) {
                    // Bonne réponse
                    answerText3.setBackgroundColor(Color.GREEN)
                    // Déclencher l'animation de célébration ici
                    confetti.start(party)

                    // passons au question suivant
                    Handler().postDelayed({
                        answerText3.setBackgroundColor(getResources().getColor(R.color.grown)) // Remettre la couleur de fond par défaut
                        btnNext.performClick()
                    },500)
                } else {
                    // Mauvaise réponse
                    answerText3.setBackgroundColor(Color.RED)
                    vibrateDevice()
                }
                // Désactiver les autres réponses
                answerText1.isEnabled = false
                answerText2.isEnabled = false

                // Utilisation d'un Handler pour rétablir les valeurs par défaut après un délai de 2 secondes
                Handler().postDelayed({
                    // Rétablir les valeurs par défaut après 2 secondes
                    answerText3.setBackgroundColor(getResources().getColor(R.color.grown)) // Remettre la couleur de fond par défaut
                    answerText1.isEnabled = true // Activer à nouveau les autres réponses
                    answerText2.isEnabled = true
                }, 1000) // Délai de 2000 ms (2 secondes)
            }
        }

        plusBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCardActivity::class.java)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
            startActivityForResult(intent, addRequest)
        }

        editBtn.setOnClickListener {
            if (allFlashcards.isNotEmpty()) {
                val (question, answer, wrongAnswer1, wrongAnswer2, uuid) = allFlashcards[currentFlashcardIndex]

                val intent = Intent(this@MainActivity, AddCardActivity::class.java)
                intent.putExtra("isUpdate", true)
                intent.putExtra("uuid", uuid) // Passer l'UUID de la carte à éditer
                intent.putExtra("question", question)
                intent.putExtra("answer1", answer)
                intent.putExtra("answer2", wrongAnswer1)
                intent.putExtra("answer3", wrongAnswer2)
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                startActivityForResult(intent, editRequest)
            } else {
                Snackbar.make(findViewById(android.R.id.content), "No card to edit.", Snackbar.LENGTH_LONG).show()
            }
        }

        btnNext.setOnClickListener {
            if (allFlashcards.isEmpty()) {
                return@setOnClickListener // Retourner si la liste est vide
            }

            // Afficher la carte suivante
            currentFlashcardIndex = getRandomNumber(maxNumber = allFlashcards.size - 1)

            // Revenir à la première carte si nous avons atteint la fin
            if (currentFlashcardIndex >= allFlashcards.size) {
                currentFlashcardIndex = 0
            }

            // Afficher un message si la liste ne contient qu'une seule carte
            if (allFlashcards.size == 1) {
                Snackbar.make(findViewById(android.R.id.content), "No more questions.", Snackbar.LENGTH_LONG).show()
            }

            displayFlashcard(currentFlashcardIndex)
        }


        btnDelete.setOnClickListener {
            if (allFlashcards.isEmpty()) {
                return@setOnClickListener // Retourner si la liste est vide
            }

            val (question) = allFlashcards[currentFlashcardIndex]

            // Supprimer la carte actuelle
            flashcardDatabase.deleteCard(question)
            allFlashcards.clear()
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            if (allFlashcards.isEmpty()) {
                // Si la liste est vide après la suppression, effacez les champs de texte et ne pas afficher de carte
                questionText.text = ""
                answerText1.text = ""
                answerText2.text = ""
                answerText3.text = ""
            } else {
                // Assurez-vous que l'index de la carte actuelle reste valide après la suppression
                if (currentFlashcardIndex >= allFlashcards.size) {
                    currentFlashcardIndex = 0 // Revenir à la première carte si nous avons supprimé la dernière
                }
                displayFlashcard(currentFlashcardIndex)
            }
        }


    }

    private fun displayFlashcard(index: Int) {
        if (allFlashcards.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Database is empty", Snackbar.LENGTH_LONG).show()
            return
        }
        startTimer()


        val (question, answer, wrongAnswer1, wrongAnswer2) = allFlashcards[index]

        val questionText = findViewById<TextView>(R.id.question_txt)
        val answerText1 = findViewById<TextView>(R.id.answer1_txt)
        val answerText2 = findViewById<TextView>(R.id.answer2_txt)
        val answerText3 = findViewById<TextView>(R.id.answer3_txt)

        questionText.text = question

        val answers = mutableListOf(answer, wrongAnswer1, wrongAnswer2)
        answers.shuffle() // Mélanger les réponses

        answerText1.text = answers[0]
        answerText2.text = answers[1]
        answerText3.text = answers[2]

        // animation in text view
        questionText.startAnimation(rightOutAnim)
        answerText1.startAnimation(rightOutAnim)
        answerText2.startAnimation(rightOutAnim)
        answerText3.startAnimation(rightOutAnim)

    }

    private fun getRandomNumber(minNumber: Int = 0, maxNumber: Int): Int {
        if (maxNumber == minNumber) {
            return minNumber // S'il n'y a qu'un seul élément, retournez simplement cet élément
        }
        var randomNumber: Int
        do {
            randomNumber = (minNumber..maxNumber).random()
        } while (randomNumber == currentFlashcardIndex)

        return randomNumber
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer?.start()
    }

    @Suppress("DEPRECATION")
    private fun vibrateDevice() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

        if (vibrator?.hasVibrator() == true) {
            val duration = 1000L // Durée de la vibration

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.EFFECT_DOUBLE_CLICK))
            } else {
                vibrator.vibrate(duration)
            }
        }
    }

}
