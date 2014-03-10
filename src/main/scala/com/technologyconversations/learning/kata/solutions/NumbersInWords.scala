package com.technologyconversations.learning.kata.solutions

class NumbersInWords {

  private[solutions] val numbersMap = Map(
    0 -> "", 1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four",
    5 -> "five", 6 -> "six", 7 -> "seven", 8 -> "eight", 9 -> "nine",
    10 -> "ten", 11 -> "eleven", 12 -> "twelve", 13 -> "thirteen", 14 -> "fourteen",
    15 -> "fifteen", 16 -> "sixteen", 17 -> "seventeen", 18 -> "eighteen", 19 -> "nineteen",
    20 -> "twenty", 30 -> "thirty", 40 -> "forty",
    50 -> "fifty", 60 -> "sixty", 70 -> "seventy", 80 -> "eighty", 90 -> "ninety",
    100 -> "hundred", 1000 -> "thousand", 1000000 -> "million"
  )

  private[solutions] val wordsMap = numbersMap.map(_.swap)

  private[solutions] val multipliers = List(1000000, 1000, 100, 10, 1, 0)

  def numbersToWords(amount: Int): String = {
    if (amount > 0) numbersToWords(amount, "").trim
    else ""
  }

  def wordsToNumbers(amount: String): Int = {
    wordsToNumbers(amount.split(" ").toList, 0, 0)
  }

  private[solutions] def numbersToWords(amount: Int, words: String): String = {
    val multiplier = multipliers.filter(_ <= amount).head
    lazy val times = (amount - (amount % multiplier)) / multiplier
    lazy val currentAmount = times * multiplier
    lazy val newAmount = amount % multiplier
    if (amount < 20) {
      return words + " " + numbersMap(amount)
    } else if (amount < 100) {
      return numbersToWords(newAmount, words + " " + numbersMap(currentAmount))
    } else if (amount >= 1000 && amount < 1000000) {
      return numbersToWords(newAmount, words + " " + numbersToWords(times) + " " + numbersMap(1000))
    } else {
      return numbersToWords(newAmount, words + " " + numbersMap(times) + " " + numbersMap(multiplier))
    }
    words
  }

  private[solutions] def wordsToNumbers(amount: List[String], numbers: Int, prevNumber: Int): Int = {
    if (amount == Nil) numbers + prevNumber
    else {
      val currentWords = amount.head.trim
      if (currentWords.isEmpty || !wordsMap.contains(currentWords)) {
        0
      } else {
        val newAmount = amount.tail
        val currNumber = wordsMap(currentWords)
        if (currNumber < 10) {
          wordsToNumbers(newAmount, numbers, currNumber)
        } else if (currNumber == 1000) {
          val currTotal = numbers + prevNumber
          val nonMillions = currTotal % 1000000
          val millions = currTotal - nonMillions
          wordsToNumbers(newAmount, (nonMillions * currNumber) + millions, 0)
        } else if (prevNumber > 0) {
          wordsToNumbers(newAmount, numbers + (currNumber * prevNumber), 0)
        } else {
          wordsToNumbers(newAmount, numbers + currNumber, 0)
        }
      }
    }
  }

}
object NumbersInWords {

  def apply() = { new NumbersInWords }

}
