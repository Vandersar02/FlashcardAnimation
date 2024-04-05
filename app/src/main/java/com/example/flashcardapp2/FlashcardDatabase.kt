package com.example.flashcardapp2

import android.content.Context
import androidx.room.Room

class FlashcardDatabase internal constructor(context: Context) {

    private val db: AppDatabase

    fun initFirstCard() {
        if (db.flashcardDao().getAll().isEmpty()) {
            insertCard(Flashcard("Who is the 44th President of the United States", "Barack Obama", "Georges H. W. Bush", "Bill Clinton"))
//            insertCard(Flashcard("What is the capital of France?", "Paris", "London", "Berlin"))
//            insertCard(Flashcard("Who wrote 'Romeo and Juliet'?", "William Shakespeare", "Charles Dickens", "Jane Austen"))
//            insertCard(Flashcard("What is the chemical symbol for water?", "H2O", "CO2", "NaCl"))
//            insertCard(Flashcard("Which planet is known as the Red Planet?", "Mars", "Jupiter", "Venus"))
//            insertCard(Flashcard("What is the tallest mountain in the world?", "Mount Everest", "K2", "Kangchenjunga"))
//            insertCard(Flashcard("Who painted the Mona Lisa?", "Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso"))
//            insertCard(Flashcard("What is the chemical symbol for gold?", "Au", "Ag", "Cu"))
//            insertCard(Flashcard("Who was the first person to step on the Moon?", "Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin"))
//            insertCard(Flashcard("Which country is famous for the pyramids?", "Egypt", "Mexico", "Greece"))
//            insertCard(Flashcard("Who invented the telephone?", "Alexander Graham Bell", "Thomas Edison", "Nikola Tesla"))
        }
    }

    fun getAllCards(): List<Flashcard> {
        return db.flashcardDao().getAll()
    }

    fun insertCard(flashcard: Flashcard) {
        db.flashcardDao().insertAll(flashcard)
    }

    fun deleteCard(flashcardQuestion: String) {
        val allCards = db.flashcardDao().getAll()
        for (card in allCards) {
            if (card.question == flashcardQuestion) {
                db.flashcardDao().delete(card)
            }
        }
    }

    fun updateCard(Flashcard: Flashcard) {
        db.flashcardDao().update(Flashcard)
    }

    fun deleteAll() {
        for (flashcard in db.flashcardDao().getAll()) {
            db.flashcardDao().delete(flashcard)
        }
    }

    init {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "flashcard-database"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
