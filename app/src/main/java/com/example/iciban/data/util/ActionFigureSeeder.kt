package com.example.iciban.data.util

import com.example.iciban.R
import com.example.iciban.data.model.ActionFigure

object ActionFigureSeeder {
    val listOfActionFigure = listOf(
        ActionFigure(
            "Uzumaki Naturo Limited Edition",
            R.drawable.naturo_action_fig,
            5.0f,
            1,
            2,
            "Naruto follows the journey of Naruto Uzumaki, a young ninja striving to gain recognition from his village and become the Hokage. Along the way, he faces numerous enemies, hones his skills, and searches for his true identity.",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Suske",
            R.drawable.action_fig_suske,
            5.0f,
            2, 2,
            "A lone avenger from the prestigious Uchiha clan, Suske's life was forever changed after the massacre of his crewmates. Consumed by a desire for revenge, he left his village to gain power and confront the impostor. Though cold and distant, Suske is an elite ninja, skilled in lightning-style techniques and the powerful Emergency button",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Tsunadere",
            R.drawable.action_fig_tsunadere,
            4.0f,
            1,
            4,
            " The legendary Sannin and fifth Hokage of Konohagakure, Tsunadere is a renowned medical-nin and the world’s strongest kunoichi. Her heritage as a Senju gives her immense power, and her leadership has saved her village from destruction. Tsunadere’s love for her comrades and unshakable will drives her to protect her village at all costs.",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Killer bee",
            R.drawable.action_fig_killer_bee,
            4.0f,
            2, 5,
            "A proud and eccentric shinobi from Kumogakure, Killer Bee is the Jinchuriki of the Eight-Tails, Gyuki. Known for his laid-back attitude and love of rap, Bee has an unshakable confidence and is one of the few Jinchuriki to fully control his tailed beast.",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Kagurabachi Uchiha",
            R.drawable.action_fig_itachi,
            4.0f,
            2,
            5,
            "Once hailed as a prodigy of the Uchiha clan, Itachi bears the burden of his tragic decision to annihilate his entire clan to prevent a greater disaster. Feared as a traitor and criminal, Kagurabachi wields the Mangekyo Sharingan, granting him unparalleled abilities. Despite his dark past, Itachi’s actions were rooted in a desire for peace.",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Henariya",
            R.drawable.action_fig_henariya,
            3.0f,
            1,
            3,
            "A legendary ninja and one of the famed Three Sannin of Konohagakure, Jiraiya was a master of ninjutsu, known for his powerful toad summoning techniques and expertise in sealing jutsu. Despite his often goofy and perverted demeanor, Jiraiya was a wise and compassionate mentor who played a crucial role in shaping some of the most influential figures in the ninja world, including the Fourth Hokage and Naruto Uzumaki.",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Kakashi Hakete",
            R.drawable.action_fig_kakashihakete,
            4.0f,
            1,
            2,
            "Known as the Copy Ninja, Kakashi is a highly skilled shinobi who mastered over 1,000 techniques using his Sharingan. As a former ANBU captain and the teacher of Team 7, Kakashi is respected for his intelligence, battle experience, and calm demeanor. Though he carries the weight of many losses, he is committed to guiding the next generation.",
            "NATORU SHIPPUDEN"
        ),
        ActionFigure(
            "Ruffy",
            R.drawable.action_fig_ruffy,
            4.0f,
            1,
            3,
            "The captain of the Straw Hat Pirates, Ruffy is a fearless and carefree pirate with a big heart and even bigger dreams. His ultimate goal is to find the legendary One Piece and become the King of the Pirates. After eating the Gomu Gomu no Mi (a Devil Fruit), Ruffy gained the ability to stretch his body like rubber, making him incredibly versatile in combat. Despite his goofy and laid-back personality, Ruffy has a strong sense of justice and is fiercely loyal to his friends, always willing to go to great lengths to protect them. His boundless optimism, unwavering determination, and love for adventure make him an inspirational leader to his crew.",
            "1/2NEPIECE"
        ),
        ActionFigure(
            "Broke",
            R.drawable.action_fig_broke,
            1.0f,
            1,
            4,
            "The Straw Hats' eccentric musician, Broke is an animated skeleton who wields a cane sword and has a penchant for cracking bone-related puns. He ate the Yomi Yomi no Mi, a Devil Fruit that allows him to return to life after dying once. But shortly after he lost all of his assets which makes him broke.",
            "1/2NEPIECE"
        )

    )

    fun getActionFigByCategory(category: String): List<ActionFigure> {
        return listOfActionFigure.filter { it.category == category }
    }

    fun getRandomActionFigByCategory(category: String): ActionFigure {
        val filteredList = getActionFigByCategory(category)
        return filteredList.random()

}

fun getActionFigByTitle(title: String): ActionFigure? {
    return listOfActionFigure.find { it.title.equals(title, ignoreCase = true) }
}


}
