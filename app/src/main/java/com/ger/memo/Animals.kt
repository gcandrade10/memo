package com.ger.memo

import android.content.Context

object Animals {

    fun get(context: Context): List<Int> {
        return when (context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getInt(DATA_SET_INT, 0)) {
            1 -> getB()
            2 -> getC()
            else -> getA()
        }
    }
    fun getB() = listOf(
        R.drawable.a10,
        R.drawable.a100,
        R.drawable.a101,
        R.drawable.a102,
        R.drawable.a103,
        R.drawable.a105,
        R.drawable.a106,
        R.drawable.a107,
        R.drawable.a108,
        R.drawable.a109,
        R.drawable.a11,
        R.drawable.a110,
        R.drawable.a111,
        R.drawable.a112,
        R.drawable.a113,
        R.drawable.a114,
        R.drawable.a115,
        R.drawable.a116,
        R.drawable.a117,
        R.drawable.a118,
        R.drawable.a119,
        R.drawable.a12,
        R.drawable.a120,
        R.drawable.a121,
        R.drawable.a122,
        R.drawable.a123,
        R.drawable.a124,
        R.drawable.a125,
        R.drawable.a126,
        R.drawable.a127,
        R.drawable.a128,
        R.drawable.a129,
        R.drawable.a13,
        R.drawable.a130,
        R.drawable.a131,
        R.drawable.a132,
        R.drawable.a133,
        R.drawable.a134,
        R.drawable.a135,
        R.drawable.a136,
        R.drawable.a137,
        R.drawable.a138,
        R.drawable.a139,
        R.drawable.a14,
        R.drawable.a140,
        R.drawable.a141,
        R.drawable.a142,
        R.drawable.a143,
        R.drawable.a144,
        R.drawable.a145,
        R.drawable.a146,
        R.drawable.a147,
        R.drawable.a148,
        R.drawable.a149,
        R.drawable.a15,
        R.drawable.a150,
        R.drawable.a151,
        R.drawable.a152,
        R.drawable.a153,
        R.drawable.a155,
        R.drawable.a156,
        R.drawable.a157,
        R.drawable.a158,
        R.drawable.a159,
        R.drawable.a16,
        R.drawable.a160,
        R.drawable.a161,
        R.drawable.a162,
        R.drawable.a163,
        R.drawable.a164,
        R.drawable.a166,
        R.drawable.a168,
        R.drawable.a169,
        R.drawable.a17,
        R.drawable.a170,
        R.drawable.a171,
        R.drawable.a172,
        R.drawable.a173,
        R.drawable.a174,
        R.drawable.a175,
        R.drawable.a176,
        R.drawable.a177,
        R.drawable.a178,
        R.drawable.a179,
        R.drawable.a18,
        R.drawable.a180,
        R.drawable.a181,
        R.drawable.a182,
        R.drawable.a183,
        R.drawable.a184,
        R.drawable.a185,
        R.drawable.a186,
        R.drawable.a187,
        R.drawable.a188,
        R.drawable.a189,
        R.drawable.a19,
        R.drawable.a191,
        R.drawable.a192,
        R.drawable.a193,
        R.drawable.a194,
        R.drawable.a195,
        R.drawable.a196,
        R.drawable.a197,
        R.drawable.a199,
        R.drawable.a2,
        R.drawable.a20,
        R.drawable.a21,
        R.drawable.a23,
        R.drawable.a24,
        R.drawable.a26,
        R.drawable.a27,
        R.drawable.a28,
        R.drawable.a29,
        R.drawable.a3,
        R.drawable.a30,
        R.drawable.a31,
        R.drawable.a32,
        R.drawable.a33,
        R.drawable.a34,
        R.drawable.a35,
        R.drawable.a36,
        R.drawable.a37,
        R.drawable.a38,
        R.drawable.a39,
        R.drawable.a4,
        R.drawable.a40,
        R.drawable.a41,
        R.drawable.a42,
        R.drawable.a43,
        R.drawable.a44,
        R.drawable.a45,
        R.drawable.a46,
        R.drawable.a47,
        R.drawable.a48,
        R.drawable.a49,
        R.drawable.a5,
        R.drawable.a50,
        R.drawable.a51,
        R.drawable.a52,
        R.drawable.a53,
        R.drawable.a54,
        R.drawable.a55,
        R.drawable.a56,
        R.drawable.a57,
        R.drawable.a58,
        R.drawable.a59,
        R.drawable.a6,
        R.drawable.a60,
        R.drawable.a61,
        R.drawable.a62,
        R.drawable.a63,
        R.drawable.a64,
        R.drawable.a65,
        R.drawable.a66,
        R.drawable.a67,
        R.drawable.a68,
        R.drawable.a69,
        R.drawable.a7,
        R.drawable.a70,
        R.drawable.a71,
        R.drawable.a72,
        R.drawable.a73,
        R.drawable.a74,
        R.drawable.a75,
        R.drawable.a76,
        R.drawable.a77,
        R.drawable.a78,
        R.drawable.a79,
        R.drawable.a8,
        R.drawable.a80,
        R.drawable.a81,
        R.drawable.a82,
        R.drawable.a83,
        R.drawable.a84,
        R.drawable.a85,
        R.drawable.a86,
        R.drawable.a87,
        R.drawable.a88,
        R.drawable.a89,
        R.drawable.a9,
        R.drawable.a90,
        R.drawable.a91,
        R.drawable.a92,
        R.drawable.a93,
        R.drawable.a94,
        R.drawable.a95,
        R.drawable.a96,
        R.drawable.a97,
        R.drawable.a98,
        R.drawable.a99
    )

    fun getA() = listOf(
        R.drawable.badger,
        R.drawable.bear,
        R.drawable.budgie,
        R.drawable.bumblebee,
        R.drawable.cat,
        R.drawable.chicken,
        R.drawable.cow,
        R.drawable.crab,
        R.drawable.cute_hamster,
        R.drawable.deer,
        R.drawable.dog,
        R.drawable.dolphin,
        R.drawable.dove,
        R.drawable.elephant,
        R.drawable.falcon,
        R.drawable.fish,
        R.drawable.flamingo,
        R.drawable.fox,
        R.drawable.frog,
        R.drawable.grasshopper,
        R.drawable.hornet,
        R.drawable.horse,
        R.drawable.hummingbird,
        R.drawable.kangaroo,
        R.drawable.ladybird,
        R.drawable.leopard,
        R.drawable.lion,
        R.drawable.llama,
        R.drawable.monarch_butterfly,
        R.drawable.mouse_animal,
        R.drawable.octopus,
        R.drawable.orca,
        R.drawable.owl,
        R.drawable.panda,
        R.drawable.parrot,
        R.drawable.peacock,
        R.drawable.pig,
        R.drawable.prawn,
        R.drawable.puffin_bird,
        R.drawable.rabbit,
        R.drawable.rhinoceros,
        R.drawable.seal,
        R.drawable.sheep,
        R.drawable.sloth,
        R.drawable.snail,
        R.drawable.snake,
        R.drawable.squirrel,
        R.drawable.starfish,
        R.drawable.stork,
        R.drawable.swan,
        R.drawable.turtle,
        R.drawable.unicorn,
        R.drawable.whale,
        R.drawable.wolf,
        R.drawable.zebra,
    )

    fun getC() = listOf(
        R.drawable.assets_cards_1,
        R.drawable.assets_cards_2,
        R.drawable.assets_cards_3,
        R.drawable.assets_cards_4,
        R.drawable.assets_cards_5,
        R.drawable.assets_cards_6,
        R.drawable.assets_cards_7,
        R.drawable.assets_cards_8,
        R.drawable.assets_cards_9,
        R.drawable.assets_cards_10,
        R.drawable.assets_cards_11,
        R.drawable.assets_cards_12,
        R.drawable.assets_cards_13,
        R.drawable.assets_cards_14,
        R.drawable.assets_cards_15,
        R.drawable.assets_cards_16,
        R.drawable.assets_cards_17,
        R.drawable.assets_cards_18,
        R.drawable.assets_cards_19,
        R.drawable.assets_cards_20,
        R.drawable.assets_cards_21,
        R.drawable.assets_cards_22,
        R.drawable.assets_cards_23,
        R.drawable.assets_cards_24,
        R.drawable.assets_cards_25,
        R.drawable.assets_cards_26,
        R.drawable.assets_cards_27,
        R.drawable.assets_cards_28,
        R.drawable.assets_cards_29,
        R.drawable.assets_cards_30,
        R.drawable.assets_cards_31,
        R.drawable.assets_cards_32,
        R.drawable.assets_cards_33,
        R.drawable.assets_cards_34,
        R.drawable.assets_cards_35,
        R.drawable.assets_cards_36,
        R.drawable.assets_cards_37,
        R.drawable.assets_cards_38,
        R.drawable.assets_cards_39,
        R.drawable.assets_cards_40,
        R.drawable.assets_cards_41,
        R.drawable.assets_cards_42,
        R.drawable.assets_cards_43,
        R.drawable.assets_cards_44,
        R.drawable.assets_cards_45,
        R.drawable.assets_cards_46,
        R.drawable.assets_cards_47,
        R.drawable.assets_cards_48,
        R.drawable.assets_cards_49,
        R.drawable.assets_cards_50,
        R.drawable.assets_cards_51,
        R.drawable.assets_cards_52,
        R.drawable.assets_cards_53,
        R.drawable.assets_cards_54,
        R.drawable.assets_cards_55,
        R.drawable.assets_cards_56,
        R.drawable.assets_cards_57
    )

    val Whites = listOf(
        R.drawable.badger,
        R.drawable.cat,
        R.drawable.chicken,
        R.drawable.panda,
        R.drawable.puffin_bird,
        R.drawable.sheep,
        R.drawable.stork,
        R.drawable.swan,
    )
    val Pinks = listOf(
        R.drawable.flamingo,
        R.drawable.hummingbird,
        R.drawable.pig,
        R.drawable.unicorn
    )
    val Yellows = listOf(
        R.drawable.bumblebee,
        R.drawable.cute_hamster,
        R.drawable.fish,
        R.drawable.hornet,
        R.drawable.leopard,
        R.drawable.snail
    )
    val Oranges = listOf(
        R.drawable.crab,
        R.drawable.fox,
        R.drawable.kangaroo,
        R.drawable.ladybird,
        R.drawable.lion,
        R.drawable.monarch_butterfly,
        R.drawable.parrot,
        R.drawable.prawn,
        R.drawable.squirrel,
        R.drawable.starfish,
    )
    val Grays = listOf(
        R.drawable.elephant,
        R.drawable.horse,
        R.drawable.mouse_animal,
        R.drawable.owl,
        R.drawable.rabbit,
        R.drawable.rhinoceros,
        R.drawable.seal,
        R.drawable.zebra
    )
    val Coffes = listOf(
        R.drawable.bear,
        R.drawable.cow,
        R.drawable.deer,
        R.drawable.dog,
        R.drawable.falcon,
        R.drawable.kiwi_bird,
        R.drawable.llama,
        R.drawable.sloth,
        R.drawable.wolf,
    )
    val Blues = listOf(
        R.drawable.dolphin,
        R.drawable.dove,
        R.drawable.octopus,
        R.drawable.orca,
        R.drawable.peacock,
        R.drawable.whale,
    )
    val Greens = listOf(
        R.drawable.alligator,
        R.drawable.budgie,
        R.drawable.octopus,
        R.drawable.frog,
        R.drawable.grasshopper,
        R.drawable.snake,
        R.drawable.turtle,
    )

    private fun generateOne(list: List<Int>, choosenOne: Int): Int {
        val copy = list.toCollection(mutableListOf())
        copy.remove(choosenOne)
        return copy.shuffled().take(1).toList().first()
    }

    private fun generateOnePerList(choosenOne: Int): List<Int> {
        val lists = listOf(Greens, Blues, Coffes, Grays, Oranges, Yellows, Pinks, Whites)
        return lists.map {
            generateOne(it, choosenOne)
        }
    }

    fun getOneRandom(context: Context) = get(context).asSequence().shuffled().take(1).toList().first()

    fun generateCard(cardSize: Int, choosenOne: Int, availableList: List<Int>): List<Int> {
        val listOne = availableList.asSequence().shuffled().take(cardSize - 1).toMutableList()
//        val listOne = generateOnePerList(choosenOne).toMutableList()
        listOne.add(choosenOne)
        return listOne.shuffled()
    }

    fun generateSecondCard(cardSize: Int, notChoosable: List<Int>, choosenOne: Int, context: Context): List<Int> {
        val list = get(context).filter { !notChoosable.contains(it) }
        return generateCard(cardSize, choosenOne, list)
    }
}